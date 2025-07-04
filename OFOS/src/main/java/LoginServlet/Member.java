package LoginServlet;

public class Member {
    private String name, email, phone, password, address;

    
    public Member() {
        super();
    }

   
    public Member(String name, String email, String phone, String password, String address) {
        super();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.address = address;
    }

    
    public Member(String name, String email, String phone, String password) {
        super();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.address = ""; 
    }

   
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
