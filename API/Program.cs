using Microsoft.EntityFrameworkCore;
using System.Text.Json;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Diagnostics.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.AspNetCore.Http;
using System.Text;
using System.Security.Cryptography;
using System.IO;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;

var builder = WebApplication.CreateBuilder(args);

// Configurar DbContext en memoria
builder.Services.AddDbContext<AppDbContext>(opt => opt.UseInMemoryDatabase("SummitTrack"));
builder.Services.AddDatabaseDeveloperPageExceptionFilter();
builder.Logging.AddConsole();

var app = builder.Build();

// 📌 Definir rutas de los archivos JSON
var dataDirectory = Path.Combine(Directory.GetCurrentDirectory(), "data");
var jsonFiles = new[] { "montes_españa.json", "montes_navarra.json", "montes_pirineos.json" };

// 📌 Cargar datos desde JSON al iniciar y eliminarlos después
using (var scope = app.Services.CreateScope())
{
    var db = scope.ServiceProvider.GetRequiredService<AppDbContext>();

    foreach (var fileName in jsonFiles)
    {
        var filePath = Path.Combine(dataDirectory, fileName);
        Console.WriteLine($"Leyendo archivo: {filePath}");

        if (File.Exists(filePath))
        {
            var jsonData = await File.ReadAllTextAsync(filePath);
            var data = JsonSerializer.Deserialize<List<Monte>>(jsonData);

            if (data != null && data.Any())
            {
                db.Montes.AddRange(data);
                await db.SaveChangesAsync();
                Console.WriteLine($"Añadidos {data.Count} montes desde {fileName}");
            }
            else
            {
                Console.WriteLine($"No se encontraron datos válidos en {fileName}");
            }
        }
        else
        {
            Console.WriteLine($"Archivo no encontrado: {filePath}");
        }
    }

}

                    /* * * * * * * * * * * * 
                     *  CRUD BASICO MONTES * 
                     * * * * * * * * * * * */

// 📌 Obtener todos los montes
app.MapGet("/montes", async (AppDbContext db) =>
    await db.Montes.ToListAsync());

// 📌 Obtener un monte por ID
app.MapGet("/montes/{id}", async (int id, AppDbContext db) =>
{
    var monte = await db.Montes.FindAsync(id);
    return monte is not null ? Results.Ok(monte) : Results.NotFound();
});

// 📌 Crear un nuevo monte
app.MapPost("/montes", async (Monte monte, AppDbContext db) =>
{
    db.Montes.Add(monte);
    await db.SaveChangesAsync();
    return Results.Created($"/montes/{monte.Id}", monte);
});

// 📌 Actualizar un monte
app.MapPut("/montes/{id}", async (int id, Monte input, AppDbContext db) =>
{
    var monte = await db.Montes.FindAsync(id);
    if (monte is null) return Results.NotFound();

    // Actualizar los campos del monte
    monte.nombre = input.nombre;
    monte.ubicacion = input.ubicacion;
    monte.altura = input.altura;

    await db.SaveChangesAsync();
    return Results.Ok(monte);
});

// 📌 Eliminar un monte
app.MapDelete("/montes/{id}", async (int id, AppDbContext db) =>
{
    var monte = await db.Montes.FindAsync(id);
    if (monte is null) return Results.NotFound();

    db.Montes.Remove(monte);
    await db.SaveChangesAsync();
    return Results.NoContent();
});


app.Run();