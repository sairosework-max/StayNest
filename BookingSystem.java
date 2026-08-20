import java.time.LocalDate;
import java.util.*;

/** Core StayNest engine. Demonstrates OOP, Collections, CRUD and evaluation workflow. */
public class BookingSystem {
    private final ArrayList<Accommodation> accommodations = new ArrayList<>();
    private final LinkedList<Booking> bookings = new LinkedList<>();
    private final Queue<Booking> pendingRequests = new LinkedList<>();
    private final HashMap<String, Accommodation> accommodationIndex = new HashMap<>();
    private final ArrayList<User> users = new ArrayList<>();
    private final HashMap<String, User> userIndex = new HashMap<>();
    private final ArrayList<Evaluation> evaluations = new ArrayList<>();
    private int nextAccommodationNumber=1, nextBookingNumber=1, nextUserNumber=11, nextEvaluationNumber=1;

    public void loadFromFiles(FileManager fm){
        accommodations.clear(); bookings.clear(); pendingRequests.clear(); users.clear(); userIndex.clear(); evaluations.clear();
        accommodations.addAll(fm.loadAccommodations()); for(Accommodation a:accommodations) accommodationIndex.put(a.getId(),a);
        users.addAll(fm.loadUsers()); for(User u:users) userIndex.put(u.getUserId(),u);
        bookings.addAll(fm.loadBookings()); for(Booking b:bookings) if(b.getStatus()==BookingStatus.PENDING) pendingRequests.add(b);
        evaluations.addAll(fm.loadEvaluations()); updateCounters();
    }
    private void updateCounters(){for(Accommodation a:accommodations) nextAccommodationNumber=Math.max(nextAccommodationNumber,num(a.getId())+1); for(Booking b:bookings) nextBookingNumber=Math.max(nextBookingNumber,num(b.getBookingId())+1); for(User u:users) nextUserNumber=Math.max(nextUserNumber,num(u.getUserId())+1); for(Evaluation e:evaluations) nextEvaluationNumber=Math.max(nextEvaluationNumber,num(e.getEvaluationId())+1);}
    private int num(String id){try{return Integer.parseInt(id.replaceAll("[^0-9]",""));}catch(Exception e){return 0;}}
    public void saveToFiles(FileManager fm){fm.saveAccommodations(accommodations);fm.saveUsers(users);fm.saveBookings(bookings);fm.saveEvaluations(evaluations);}

    public List<Accommodation> getAllAccommodations(){return Collections.unmodifiableList(accommodations);} public Accommodation addAccommodation(Accommodation a){accommodations.add(a);accommodationIndex.put(a.getId(),a);return a;}
    public Accommodation findAccommodationById(String id)throws AccommodationNotFoundException{Accommodation a=accommodationIndex.get(id);if(a==null)throw new AccommodationNotFoundException(id);return a;}
    public List<Accommodation> searchByLocation(String k){List<Accommodation> r=new ArrayList<>();for(Accommodation a:accommodations)if(a.getLocation().toLowerCase().contains(k.toLowerCase()))r.add(a);return r;}
    public List<Accommodation> searchByMaxPrice(double p){List<Accommodation> r=new ArrayList<>();for(Accommodation a:accommodations)if(a.getPricePerNight()<=p)r.add(a);return r;}
    public boolean deleteAccommodation(String id)throws AccommodationNotFoundException{Accommodation a=findAccommodationById(id);accommodations.remove(a);accommodationIndex.remove(id);return true;}
    public String generateAccommodationId(){return "A"+(nextAccommodationNumber++);}

    public List<User> getAllUsers(){return Collections.unmodifiableList(users);} public User addUser(User u){if(userIndex.containsKey(u.getUserId()))throw new InvalidInputException("User ID already exists.");users.add(u);userIndex.put(u.getUserId(),u);return u;}
    public User findUserById(String id)throws InvalidInputException{User u=userIndex.get(id);if(u==null)throw new InvalidInputException("No user found with ID: "+id);return u;}
    public List<User> searchUsers(String q){List<User> r=new ArrayList<>();for(User u:users)if(u.getUserId().equalsIgnoreCase(q)||u.getName().toLowerCase().contains(q.toLowerCase())||u.getEmail().toLowerCase().contains(q.toLowerCase()))r.add(u);return r;}
    public boolean deleteUser(String id)throws InvalidInputException{User u=findUserById(id);for(Booking b:bookings)if(b.getUserId().equalsIgnoreCase(id))throw new InvalidInputException("Cannot delete a user with existing bookings.");users.remove(u);userIndex.remove(id);return true;}
    public String generateUserId(){return "U"+(nextUserNumber++);}

    public Booking createBooking(String userId,String accommodationId,LocalDate in,LocalDate out,int guests)throws AccommodationNotFoundException,InvalidBookingException{User u=findUserById(userId);Accommodation a=findAccommodationById(accommodationId);if(!a.isAvailable())throw new InvalidBookingException("Accommodation is not currently available.");if(guests>a.getMaxGuests())throw new InvalidBookingException("Maximum guests: "+a.getMaxGuests());if(!out.isAfter(in))throw new InvalidBookingException("Check-out date must be after check-in date.");double total=a.calculateTotalPrice((int) java.time.temporal.ChronoUnit.DAYS.between(in,out));Booking b=new Booking("B"+(nextBookingNumber++),u.getUserId(),u.getName(),accommodationId,in,out,guests,total,BookingStatus.PENDING);bookings.add(b);pendingRequests.add(b);return b;}
    public List<Booking> getAllBookings(){return Collections.unmodifiableList(bookings);} public Booking findBookingById(String id)throws InvalidBookingException{for(Booking b:bookings)if(b.getBookingId().equalsIgnoreCase(id))return b;throw new InvalidBookingException("No booking found with ID: "+id);}
    public Booking processNextPendingRequest()throws InvalidBookingException{Booking b=pendingRequests.poll();if(b==null)throw new InvalidBookingException("No pending booking requests.");b.setStatus(BookingStatus.CONFIRMED);return b;}
    public int countPendingRequests(){return pendingRequests.size();}
    public void cancelBooking(String id)throws InvalidBookingException{Booking b=findBookingById(id);b.setStatus(BookingStatus.CANCELLED);pendingRequests.remove(b);} public void updateBookingStatus(String id,BookingStatus s)throws InvalidBookingException{Booking b=findBookingById(id);b.setStatus(s);if(s!=BookingStatus.PENDING)pendingRequests.remove(b);}
    public boolean deleteBooking(String id)throws InvalidBookingException{Booking b=findBookingById(id);bookings.remove(b);pendingRequests.remove(b);return true;}

    public List<Evaluation> getAllEvaluations(){return Collections.unmodifiableList(evaluations);} public String generateEvaluationId(){return "E"+(nextEvaluationNumber++);}
    public Evaluation addEvaluation(String bookingId,int rating,String feedback)throws InvalidBookingException{Booking b=findBookingById(bookingId);if(b.getStatus()!=BookingStatus.COMPLETED)throw new InvalidBookingException("Only completed bookings can be evaluated.");for(Evaluation e:evaluations)if(e.getBookingId().equalsIgnoreCase(bookingId))throw new InvalidBookingException("This booking already has an evaluation.");User u=findUserById(b.getUserId());String outcome;int points=0;if(rating>=4){outcome="POSITIVE FEEDBACK - REWARD";points=10;u.addRewardPoints(points);}else if(rating<=2){outcome="NEGATIVE FEEDBACK - PENALTY";u.addPenalty();}else outcome="NEUTRAL FEEDBACK";Evaluation e=new Evaluation(generateEvaluationId(),bookingId,u.getUserId(),rating,feedback,outcome,points);evaluations.add(e);return e;}
}
