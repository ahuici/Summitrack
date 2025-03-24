
public class Monte
{
    public int Id { get; set; }
    public string? nombre { get; set; } = string.Empty;
    public string? provincia { get; set; } = string.Empty;
    public int altura { get; set; }


    public Monte()
    {
    }

    public Monte(int id, String nombre, String provincia, int altura)
    {
        Id = id;
        this.nombre = nombre;
        this.provincia = provincia;
        this.altura = altura;
    }
}