package br.com.rotas.app;

/**
 * 
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
public class Vertice {
	
	private String id;

	public Vertice(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        
        if (object == null)
            return false;
        
        if (getClass() != object.getClass())
            return false;
        
        Vertice verticeToCompare = (Vertice) object;
        
        if (id != null && id.equals(verticeToCompare.id)) {
        	return true;
        }
        
        return false;
    }

	@Override
	public String toString() {
		return String.valueOf(this.id);
	}
}
