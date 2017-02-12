package datalayer;

import java.sql.ResultSet;
import java.util.List;



public interface Entity_manager<T> {
	public T setModel_selectField(String element,T temps ,
			ResultSet rs);
	public List<T> selectField(List<String> list, String stm);
	public Boolean delete(String stm);
	public List <T> selectAll(String stm,Boolean call);
	}
