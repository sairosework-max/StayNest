# StayNest Accommodation Booking System

## Project Overview
A text-based, Airbnb-style accommodation booking system developed as a Java console application for ICT711 Assessment 3. The system manages property listings, user accounts, bookings, and feedback with reward/penalty mechanisms.

## Team Members
Name	Student ID
Sairose Mehbub Sasshoo	12300760
Prakash Kharel	20037255
Mohan Rawat	20040882
Umair Abdul Qayyum	20034554
Sujit Rana Magar	20039197


## Technical Requirements
- **Java Version**: JDK 11+ (developed with Java 21)
- **Dependencies**: No external libraries - uses only standard library
- **Build Tool**: Manual compilation with javac

## Compilation and Execution
```bash
cd src
javac -d ../bin *.java
cd ../bin
cp -r ../data .
java -cp . Main
```

## Project Structure
```
src/
├── Accommodation.java              # Abstract base class
├── Apartment.java                  # Subclass
├── House.java                      # Subclass
├── Villa.java                      # Subclass
├── User.java                       # User management
├── Booking.java                    # Reservation model
├── BookingStatus.java              # Enum: PENDING, CONFIRMED, CANCELLED, COMPLETED
├── BookingSystem.java              # Core business logic
├── FileManager.java                # File I/O operations
├── Main.java                       # Console UI
├── AccommodationNotFoundException.java
├── InvalidBookingException.java
├── InvalidInputException.java
└── RewardPenaltyManager.java       # Rating/reward logic

data/
├── accommodations.txt              # 12 seed properties
├── bookings.txt                    # 7 seed bookings
└── users.txt                       # 10+ users
```

## Key Features

### 1. Object-Oriented Design
- **Inheritance**: Apartment, House, Villa extend abstract Accommodation
- **Polymorphism**: `calculateTotalPrice()` overridden per property type
- **Abstraction**: Accommodation defines abstract methods for price, category
- **Encapsulation**: All fields private with validation in setters

### 2. Collections API Usage
- `ArrayList<Accommodation>`: Property catalog (read-heavy, indexed)
- `LinkedList<Booking>`: Booking history (frequent appends)
- `Queue<Booking>`: Pending requests (FIFO processing)
- `HashMap<String, Accommodation>`: O(1) property lookup by ID
- `ArrayList<User>`: User management

### 3. File I/O Operations
- Reads/writes text files in pipe-delimited format
- Uses try-with-resources and BufferedReader/BufferedWriter
- Graceful error handling for malformed lines

### 4. Exception Handling
- **Checked Exceptions**: AccommodationNotFoundException, InvalidBookingException
- **Unchecked Exception**: InvalidInputException (RuntimeException)
- **File I/O**: Catches IOException, continues processing valid data
- **User Input**: Wrapped in try/catch with re-prompt on error

### 5. CRUD Operations

| Entity        | Create | Read | Update | Delete |
|---------------|--------|------|--------|--------|
| Accommodation | ✅      | ✅    | ✅      | ✅      |
| Booking       | ✅      | ✅    | ✅      | ✅      |
| User          | ✅      | ✅    | ✅      | ✅      |

### 6. Rating & Reward System
- Completed bookings can be rated 1-5
- **Rewards**: Ratings 4-5 → +10 reward points
- **Penalties**: Ratings 1-2 → recorded penalty
- **Neutral**: Rating 3 → no action

## Core Functionality

### Property Management
- Add/Update/Delete accommodations
- Search by location or maximum price
- View all properties with details

### Booking Management
- Create booking requests (pending queue)
- Process pending requests (FIFO)
- Update booking statuses
- Cancel/delete bookings

### User Management
- Add/view/search/update/delete users
- View reward points and penalties

## Customization Checklist
- [ ] Replace placeholder guest names with actual group members
- [ ] Add names to header comments
- [ ] Adjust pricing rules if needed
- [ ] Modify menu wording for originality
- [ ] Test all functionality locally

## Testing Notes
- Compile and run with provided seed data
- Verify all CRUD operations
- Test rating/reward system
- Validate file persistence
- Ensure exception handling works

## Important Academic Note
This project is a reference scaffold. Each team member must understand and explain every class. Modify attributes, methods, and features to make it your own before submission.

---

*Developed for ICT711 Assessment 3 - Java Application Group Project*
