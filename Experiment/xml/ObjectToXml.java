package xml;

import java.io.FileOutputStream;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
class Employee {  
	    private int id;  
	    private String name;  
	    private float salary;  
	  
	public Employee() {}  
	public Employee(int id, String name, float salary) {  
	    super();  
	    this.id = id;  
	    this.name = name;  
	    this.salary = salary;  
	}  
	@XmlAttribute  
	public int getId() {  
	    return id;  
	}  
	public void setId(int id) {  
	    this.id = id;  
	}  
	@XmlElement  
	public String getName() {  
	    return name;  
	}  
	public void setName(String name) {  
	    this.name = name;  
	}  
	@XmlElement  
	public float getSalary() {  
	    return salary;  
	}  
	public void setSalary(float salary) {  
	    this.salary = salary;  
	}  
	public static void main(String[] args) throws Exception{  
		
	}
}


class ObjectToXml {  
	public static void main(String[] args) throws Exception{  
		JAXBContext contextObj = JAXBContext.newInstance(Employee.class);  

		Marshaller marshallerObj = contextObj.createMarshaller();  
		marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  

		Employee emp1=new Employee(1,"Vimal Jaiswal",50000);  

		marshallerObj.marshal(emp1, new FileOutputStream("employee.xml"));  

	}  
}  

