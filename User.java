/** Represents a registered StayNest user. */
public class User {
    private String userId;
    private String name;
    private String email;
    private String phone;
    private int rewardPoints;
    private int penaltyCount;

    public User(String userId, String name, String email, String phone) {
        this.userId = require(userId, "User ID");
        setName(name); setEmail(email); setPhone(phone);
    }
    private String require(String value, String field) {
        if (value == null || value.trim().isEmpty()) throw new InvalidInputException(field + " cannot be empty.");
        return value.trim();
    }
    public String getUserId(){ return userId; }
    public String getName(){ return name; }
    public String getEmail(){ return email; }
    public String getPhone(){ return phone; }
    public int getRewardPoints(){ return rewardPoints; }
    public int getPenaltyCount(){ return penaltyCount; }
    public void setName(String v){ name=require(v,"Name"); }
    public void setEmail(String v){ email=require(v,"Email"); }
    public void setPhone(String v){ phone=require(v,"Phone"); }
    public void addRewardPoints(int points){ if(points>0) rewardPoints += points; }
    public void addPenalty(){ penaltyCount++; }
    public String toFileString(){ return String.join("|", userId,name,email,phone,String.valueOf(rewardPoints),String.valueOf(penaltyCount)); }
    public static User fromFileString(String line){
        String[] p=line.split("\\|");
        if(p.length<4) throw new InvalidInputException("Malformed user record: "+line);
        User u=new User(p[0],p[1],p[2],p[3]);
        if(p.length>4) try{u.rewardPoints=Integer.parseInt(p[4]);}catch(NumberFormatException ignored){}
        if(p.length>5) try{u.penaltyCount=Integer.parseInt(p[5]);}catch(NumberFormatException ignored){}
        return u;
    }
    @Override public String toString(){ return String.format("[%s] %-20s | %-28s | Phone: %-12s | Rewards: %d | Penalties: %d",userId,name,email,phone,rewardPoints,penaltyCount); }
}
