/** Stores a completed booking evaluation and applies feedback consequences. */
public class Evaluation {
    private String evaluationId;
    private String bookingId;
    private String userId;
    private int rating;
    private String feedback;
    private String outcome;
    private int rewardPoints;

    public Evaluation(String evaluationId,String bookingId,String userId,int rating,String feedback,String outcome,int rewardPoints){
        this.evaluationId=evaluationId; this.bookingId=bookingId; this.userId=userId; setRating(rating); this.feedback=feedback; this.outcome=outcome; this.rewardPoints=rewardPoints;
    }
    public String getEvaluationId(){return evaluationId;} public String getBookingId(){return bookingId;} public String getUserId(){return userId;}
    public int getRating(){return rating;} public String getFeedback(){return feedback;} public String getOutcome(){return outcome;} public int getRewardPoints(){return rewardPoints;}
    public void setRating(int rating){if(rating<1||rating>5) throw new InvalidInputException("Rating must be between 1 and 5."); this.rating=rating;}
    public String toFileString(){return String.join("|",evaluationId,bookingId,userId,String.valueOf(rating),feedback.replace("|","/"),outcome,String.valueOf(rewardPoints));}
    public static Evaluation fromFileString(String line){String[] p=line.split("\\|",-1); if(p.length!=7) throw new InvalidInputException("Malformed evaluation record: "+line); return new Evaluation(p[0],p[1],p[2],Integer.parseInt(p[3]),p[4],p[5],Integer.parseInt(p[6]));}
    @Override public String toString(){return String.format("[%s] Booking: %s | User: %s | Rating: %d/5 | %s | Outcome: %s | Reward: %d",evaluationId,bookingId,userId,rating,feedback,outcome,rewardPoints);}
}
