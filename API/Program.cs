using Microsoft.EntityFrameworkCore;
using System.Text.Json;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.AspNetCore.Http;
using System.Text;
using System.Security.Cryptography;
using System.IO;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;

// Crear el builder de la aplicación
var builder = WebApplication.CreateBuilder(args);

// 📌 Configurar DbContext en memoria (para persistencia real, usar SQLite o SQL Server)
builder.Services.AddDbContext<AppDbContext>(opt => opt.UseInMemoryDatabase("AppDatabase"));
builder.Services.AddDatabaseDeveloperPageExceptionFilter();

builder.Logging.AddConsole(); // Esto asegura que los logs aparezcan en la consola


var app = builder.Build();

// 📌 Ruta del archivo JSON
var jsonPath = Path.Combine(Directory.GetCurrentDirectory(), "data", "datos.json");

// 📌 Cargar datos desde JSON al iniciar y eliminarlo después
using (var scope = app.Services.CreateScope())
{
    var db = scope.ServiceProvider.GetRequiredService<AppDbContext>();

    if (File.Exists(jsonPath))
    {
        var jsonData = await File.ReadAllTextAsync(jsonPath);
        var data = JsonSerializer.Deserialize<DatabaseData>(jsonData);

        if (data is not null)
        {
            // Evitar duplicados al cargar datos
            if (!db.Usuarios.Any()) db.Usuarios.AddRange(data.Usuarios);
            if (!db.Inmuebles.Any()) db.Inmuebles.AddRange(data.Inmuebles);
            if (!db.Visitas.Any()) db.Visitas.AddRange(data.Visitas);

            await db.SaveChangesAsync();

            // Eliminar el archivo JSON después de cargarlo
            File.Delete(jsonPath);
        }
    }
}

// 📌 Guardar datos en JSON antes de cerrar la aplicación
async Task SaveDataToJson()
{
    using var scope = app.Services.CreateScope();
    var db = scope.ServiceProvider.GetRequiredService<AppDbContext>();

    var data = new DatabaseData
    {
        Usuarios = await db.Usuarios.ToListAsync(),
        Inmuebles = await db.Inmuebles.ToListAsync(),
        Visitas = await db.Visitas.ToListAsync()
    };

    string json = JsonSerializer.Serialize(data, new JsonSerializerOptions { WriteIndented = true });
   
    // 📂 Crear la carpeta Data/ si no existe
    var directory = Path.GetDirectoryName(jsonPath);
    if (!Directory.Exists(directory))
    {
        Directory.CreateDirectory(directory);
    }

    await File.WriteAllTextAsync(jsonPath, json);
}

// 📌 Guardar datos en JSON antes de cerrar la aplicación
var lifetime = app.Services.GetRequiredService<IHostApplicationLifetime>();
lifetime.ApplicationStopping.Register(async () =>
{
    Console.WriteLine("Guardando datos en JSON antes de salir...");
    await SaveDataToJson();
});
                    /* * * * * * * * * * * *  * 
                     *  CRUD BASICO INMUEBLES * 
                     * * * * * * * * * * * *  */

// 📌 Obtener todos los inmuebles
app.MapGet("/inmuebles", async (AppDbContext db) =>
    await db.Inmuebles.ToListAsync());

// 📌 Obtener un inmueble por ID
app.MapGet("/inmuebles/{id}", async (string id, AppDbContext db, ILogger<Program> logger) =>
{
    try
    {
        string decryptedId = AES.DecryptString(id); // Desencriptar ID recibido como string
        var inmueble = await db.Inmuebles.FindAsync(int.Parse(decryptedId));

        return inmueble is not null ? Results.Ok(inmueble) : Results.NotFound();
    }
    catch
    {
        return Results.BadRequest("ID inválido");
    }
});


// 📌 Crear un nuevo inmueble
app.MapPost("/inmuebles", async (Inmueble inmueble, AppDbContext db, ILogger<Program> logger) =>
{
    db.Inmuebles.Add(inmueble);
    await db.SaveChangesAsync();
    return Results.Created($"/inmuebles/{inmueble.Id}", inmueble);
});


// 📌 Actualizar un inmueble
app.MapPut("/inmuebles/{id}", async (string id, Inmueble input, AppDbContext db) =>
{
    id = AES.DecryptString(id);

    var inmueble = await db.Inmuebles.FindAsync(int.Parse(id));
    if (inmueble is null) return Results.NotFound();

    // Actualizar campos
    inmueble.Nombre = input.Nombre;
    inmueble.Precio = input.Precio;
    inmueble.Ubicacion = input.Ubicacion;
    inmueble.MetrosCuadrados = input.MetrosCuadrados;
    inmueble.Disponible = input.Disponible;
    inmueble.FechaConstruccion = input.FechaConstruccion;

    await db.SaveChangesAsync();
    return Results.Ok(inmueble);
});

// 📌 Eliminar un inmueble
app.MapDelete("/inmuebles/{id}", async (string id, AppDbContext db) =>
{
    id = AES.DecryptString(id);
    var inmueble = await db.Inmuebles.FindAsync(int.Parse(id));
    if (inmueble is null) return Results.NotFound();

    db.Inmuebles.Remove(inmueble);
    await db.SaveChangesAsync();
    return Results.NoContent();
});