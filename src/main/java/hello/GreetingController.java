package hello;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    Connection con;
    
    GreetingController() {
    	try{
    		Class.forName("com.mysql.jdbc.Driver");
    		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","password");
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    @RequestMapping("/greeting")
    public @ResponseBody Greeting greeting(
            @RequestParam(value="name", required=false, defaultValue="World") String name) {
    	System.out.println("==== in greeting ====");
    	
    	//int x = addName(name);
    	
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
    
    public int addName(String name) {
    	int x = 0;
    	try{
    		PreparedStatement ps = con.prepareStatement("insert into greeting (name) values(?)");
    		ps.setString(1,  name);
    		x = ps.executeUpdate();
    		
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	return x;
    }
}