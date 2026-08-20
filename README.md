# StayNest Accommodation Booking System (Java Console Application)

A text-based, Airbnb-style accommodation booking system built for
ICT711 Assessment 3 (Java Application Group Project).

> IMPORTANT — Academic integrity: your unit's brief prohibits AI writing
> the report. Check with your unit coordinator whether AI-assisted code
> is acceptable for this assessment. Treat this project as a reference/
> scaffold: read through every class, make sure every group member can
> explain it, and adapt it (attributes, methods, UI wording, extra
> features) in your own words before submitting.

## How to compile and run

Requires a JDK (Java 11+; developed against Java 21). No external
libraries are used — only the standard library.

```bash
cd src
javac -d ../bin *.java
cd ../bin
java -cp . Main
```

Run `java` from a working directory that contains a `data/` folder (or
copy the `data/` folder next to wherever you run `java Main` from) — the
program reads `data/accommodations.txt` and `data/bookings.txt` on
startup and writes back to them when you save/exit.

If you compile straight into `bin/` as above, copy the `data` folder in
too:
```bash
cp -r ../data .
java -cp . Main
```

## Project structure

```
src/
  Accommodation.java              abstract base class (abstraction + encapsulation)
  Apartment.java                  subclass (inheritance + polymorphism)
  House.java                      subclass (inheritance + polymorphism)
  Villa.java                      subclass (inheritance + polymorphism)
  Booking.java                    a single reservation
  BookingStatus.java              enum: PENDING, CONFIRMED, CANCELLED, COMPLETED
  BookingSystem.java              core logic; owns all the Collections API usage
  FileManager.java                text file reading/writing
  Main.java                       text-based CLI (menu-driven UI)
  AccommodationNotFoundException.java   custom checked exception
  InvalidBookingException.java          custom checked exception
  InvalidInputException.java            custom unchecked exception
data/
  accommodations.txt              seed data (12 properties: 4 apartments, 4 houses, 4 villas)
  bookings.txt                    seed data (7 sample bookings)
```

## How each rubric requirement is met

**Inheritance** — `Apartment`, `House`, and `Villa` all extend the abstract
`Accommodation` class and inherit its common fields/behaviour.

**Polymorphism** — `calculateTotalPrice(int nights)` and `getCategory()`
are declared abstract in `Accommodation` and overridden differently in
each subclass. `BookingSystem` and `Main` work with `Accommodation`
references throughout (e.g. `ArrayList<Accommodation>`), and calling
`accommodation.calculateTotalPrice(nights)` invokes the correct
subclass's logic at runtime without the caller needing to know which
concrete type it is.

**Abstraction** — `Accommodation` is `abstract` and can never be
instantiated directly; it defines *what* every accommodation must be
able to do (`calculateTotalPrice`, `getCategory`, `getExtraDetails`,
`toFileString`) without saying *how*, leaving that to each subclass.

**Encapsulation** — every field in every class is `private`; all access
goes through getters/setters, and setters validate input (e.g. price
can't be negative, dates must be in order) so objects can never enter
an invalid state from outside the class.

**Collections API** (see the Javadoc at the top of `BookingSystem.java`
for the full justification of each choice):
- `ArrayList<Accommodation>` — the property catalogue (read-heavy,
  indexed access).
- `LinkedList<Booking>` — the full booking history (frequent appends,
  sequential iteration).
- `Queue<Booking>` (backed by `LinkedList`) — pending booking requests
  processed strictly first-in-first-out via `poll()`.
- `HashMap<String, Accommodation>` — O(1) lookup of a property by ID.

**File I/O** — `FileManager` reads/writes `data/accommodations.txt` and
`data/bookings.txt` in a simple pipe-delimited text format, using
try-with-resources and `BufferedReader`/`BufferedWriter`.

**Exception handling** — three custom exceptions
(`AccommodationNotFoundException`, `InvalidBookingException` are
checked; `InvalidInputException` is an unchecked `RuntimeException`
used for bad input/validation). `FileManager` catches `IOException`/
`FileNotFoundException` and skips individual malformed lines instead of
crashing. `Main` wraps every menu action in try/catch and re-prompts on
bad input rather than terminating.

**Text-based UI** — `Main` implements a menu-driven CLI (main menu →
accommodation management / booking management submenus) with input
validation loops for every prompt.

## CRUD coverage

| Entity        | Create | Read (view/search) | Update | Delete |
|---------------|--------|---------------------|--------|--------|
| Accommodation | ✅ add | ✅ view all, search by location, search by max price | ✅ update | ✅ delete |
| Booking       | ✅ create booking request | ✅ view all | ✅ update status | ✅ cancel / delete |

## Things to personalise before submission

1. Replace the "Group Member One/Two/Three" placeholder guest names in
   `data/bookings.txt` and add your **actual group members' names**
   into the seed data somewhere (the brief asks for this).
2. Add your names to the header comment in `Main.java`.
3. Adjust attributes/pricing rules/menu wording if you want the system
   to feel more like your own design rather than a template.
4. Compile and test locally — this code has been carefully hand-checked
   for balanced braces/parens and correct exception declarations, but
   it has **not** been run through an actual `javac`/JVM in this
   environment (no compiler was available), so please compile and test
   it yourselves before relying on it.


## Added assessment features
- User management: add, view/search, update and delete users.
- Evaluation and feedback: completed bookings can be rated from 1 to 5.
- Reward/penalty workflow: ratings 4-5 award 10 reward points; ratings 1-2 record a penalty; rating 3 is neutral.
- users.txt contains more than 10 users as required for the file-handling demonstration.
