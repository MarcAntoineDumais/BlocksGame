package rendering;

public class Vector3i {
	public int x, y, z;
	
	public Vector3i(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof Vector3i))
            return false;

        Vector3i v = (Vector3i) obj;
        return x == v.x && y == v.y && z == v.z;
	}
	
	@Override
	public int hashCode() {
		return x + 7 * y + 13 * z;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + ")";
	}
}
