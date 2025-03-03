public class Pokemon {
    private String nombre;
    private String tipo;
    private int vida;
    private int ataque;
    private int defensa;
    private int ataqueEspecial;
    private int defensaEspecial;
    
    public Pokemon(String nombre, String tipo, int vida, int ataque, int defensa, int ataqueEspecial, int defensaEspecial) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.vida = vida;
        this.ataque = ataque;
        this.defensa = defensa;
        this.ataqueEspecial = ataqueEspecial;
        this.defensaEspecial = defensaEspecial;
    }
    
    public void atacar(Pokemon enemigo) {
        int daño = this.ataque - enemigo.defensa;
        if (daño > 0) {
            enemigo.recibirDaño(daño);
        }
    }
    
    public void recibirDaño(int daño) {
        this.vida -= daño;
        if (this.vida < 0) {
            this.vida = 0;
        }
    }
    
    public boolean esKO() {
        return this.vida == 0;
    }
}
