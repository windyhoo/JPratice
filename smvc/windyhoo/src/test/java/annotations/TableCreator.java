package annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TableCreator {

	public static void main(String[] args) throws Exception {
		String className = "annotations.Member";
		Class<?> cl = Class.forName(className);
		
		//通过类名获取注解
		DBTable dbTable = cl.getAnnotation(DBTable.class);
		
		if(dbTable == null) {
			System.out.println("no DBTable annotation in class" + className);
		}
		
		String tableName = dbTable.name();
		if(tableName.length()<1) {
			tableName = cl.getName().toUpperCase();
		}
		
		List<String> columnDefs = new ArrayList<String>();
		
		//通过类名获取注解的属性
		for(Field field:cl.getDeclaredFields()) {
			String columnName = null;
			
			//一个属性上面可能有多个注解
			Annotation[] anns = field.getDeclaredAnnotations();
			if(anns.length<1) {
				continue;
			}
			
			if(anns[0] instanceof SqlInteger) {
				SqlInteger sInt = (SqlInteger) anns[0];
				
				if(sInt.name().length()<1) {
					columnName = field.getName().toUpperCase();
				} else {
					columnName = sInt.name();
				}
				
				columnDefs.add(columnName + " INT" + getConstraints(sInt.constraints()));
			}
			
			if(anns[0] instanceof SqlString) {
				SqlString sString = (SqlString) anns[0];
				
				if(sString.name().length()<1) {
					columnName = field.getName().toUpperCase();
				} else {
					columnName = sString.name();
				}
				
				columnDefs.add(columnName + " VARCHAR(" + sString.value() + ")" + getConstraints(sString.constraints()));
			}
		}
		
		StringBuilder createCommand = new StringBuilder("create table "+ tableName + "(");
		
		for(String columnDef : columnDefs) {
			createCommand.append("\n\t"+columnDef+",");
		}
		
		String tableCreate = createCommand.substring(0,createCommand.length()-1)+");";
		System.out.println("sql:"+tableCreate);
	}

	
	private static String getConstraints(Constraints con) {
		String constraints = "";
		if(!con.allowNull()) {
			constraints += " NOT NULL";
		}
		
		if(con.primaryKey()) {
			constraints += " PRIMARY KEY";
		}
		
		if(con.unique()) {
			constraints += " UNIQUE";
		}
		
		return constraints;
	}
}
