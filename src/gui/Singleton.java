package gui;



public class Singleton {
    private static Singleton instance = new Singleton();
    public static Singleton getInstance(){
        return instance;
    }

    private String primeiroJogar;   // variavel a ser usada entre controladores
   

  

    public String getPrimeiroJogar() {
       return primeiroJogar;
    }

   public void setPrimeiroJogar(String primeiroJogar) {
       this.primeiroJogar = primeiroJogar;
   }

}