public class Employee {
    public String employeeName;
    public boolean onDelivery;

    public Employee()
    {
        employeeName = "";
        onDelivery = false;
    }

    public Employee( String name, boolean onDelivery)
    {
        employeeName = name;
        this.onDelivery = onDelivery;
    }

    public String getEmployeeName() {return employeeName;}
    public boolean getOnDelivery() {return onDelivery;}

    public void setEmployeeName(String employeeName) {this.employeeName = employeeName;}
    public void setOnDelivery(boolean onDelivery) {this.onDelivery = onDelivery;}
}