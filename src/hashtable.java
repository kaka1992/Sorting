import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class hashtable {
	public  void main(String[] args){
		
	}
	

}
/*
class hashtableImpl{
	private int container = 10000;
	private int rehashnum = (int) (0.7*container);
	private int persentsize = 0;
	private kvList[] table1;
	private kvList[] table2;
	
	public hashtableImpl() {
		// TODO Auto-generated constructor stub
		table1 = new kvList[container];
	}
	
	public boolean put(String key, Object value){
		int hashcode = key.hashCode()/container;
		List<kv> list = table1[hashcode].kvlist;
		list.add(new kv());
		
	}
	
	private class kv{
		private String key;
		private Object value;
		public kv(String key, Object value) {
			// TODO Auto-generated constructor stub
			this.key = key;
			this.value = value;
		}
	}
	
	private class kvList{
		private List<kv> kvlist;
		public kvList() {
			// TODO Auto-generated constructor stub
			kvlist = new ArrayList<kv>();
		}
	}
	
}
*/

class table2<K, V, E> implements Map<K, V>, Iterator<E>{

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E next() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<K> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V put(K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public V remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}
	
}


