# CSC207-Software-Design Final Project

# Course Description
An introduction to software design and development concepts, methods, and tools using a statically-typed object-oriented programming language such as Java. Topics from: version control, unit testing, refactoring, object-oriented design and development, design patterns, advanced IDE usage, regular expressions, and reflection. Representation of floating-point numbers and introduction to numerical computation.

# Final Project: Conference-System

![GitHub Release Date](https://img.shields.io/github/release-date/jimschenchen/Conference-System)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/jimschenchen/Conference-System)

## Description

Welcome to the Group 0173 Conference System! \
This is a java project from CSC207 course of University of Toronto. \
Requirement: [click here](https://github.com/jimschenchen/Conference-System/blob/main/ProjectRequirement.md)

## Group Member

+ [gr4ce](https://github.com/intezzz)
+ [dengzey2](https://github.com/LyannaDZY)
+ [tangzhen](https://github.com/TZ-zzz)
+ [Jinxuan](https://github.com/Jenna-wang-0965)
+ shmanova
+ [shenliha](https://github.com/baqiaoabc)
+ [jimschenchen](https://github.com/jimschenchen)

## Startup

### Database config

`pharse2/src/gateway/Config`: Set `DATABASE_URL`, `DATABASE_PORT`, and `DATABASE_PASSWORD`

### Install Dependency
1. Right click the `"group_0173" folder` in the `Project Panel` on the left part of view.
2. Click `Open Module Setting`
3. Project `setting` -> `Modules` -> `Dependencies`
4. Click the `add` button on the bottom
5. Choose `Add JARs and directories`
6. Set the filepath to the `Group_0173 Dependency` folder under the `phase2` folder.
7. If step 6 not work. Download all the jars from https://drive.google.com/drive/folders/1ALV-urNWRw3_SBN5bXW4HeRPeGiqDvxt?usp=sharing and put them in a folder and set the filepath to that folder.
8. Click `Apply` and Done!

### And please follow instructions below:
#### User

  - To sign up as an `Attendee`, you can just sign up directly from our GUI and your username and password should be at least 6 characters. 
  
  - If you want to sign up as a `Speaker`, `Organizer` or `Vip User`, please contact an existing `Organizer` (Use the `Organizer` account we provided)

#### Event
    
    1.1 To create a new event or cancel an existing event, you have to be an `Organizer`. \
      You have to assign the new event with capcity, roomId, start and end time \
      (`Note` the start time need be before end time and current time, the capacity of event needs to less than the capacity of room\
      Also, the `NonSpeakerEvent` can't be created with a speaker and the `MultiSpeakerEvent` can't be created with less than 2 speakers)
    
    1.2 You can make changes to existing event. You can assign the event with new settings include the speaker, capacity and VIP status\
        (`Note` You can change speaker for 'OneSpeakerEvent' but you can only add new speaker to `MultiSpeakerEvent`)

#### Room
    Each room has a room number and capacity. For example, you can create a room with room number BA1020 with capacity 10.

#### Message
    The Message system in this Coference System is based on the description in Phase2 instruction.

### Credentials for organizer account
`username` user\
`password` password

\
`Note` You can find all functionalities in the GUI. Please follow the instruction above to use this conference system


## Feature List:
Below I will list all the extension we did in phase2:
1. Any features in the mandatory extensions i.e.

    1.1 add a new type of user `Vip User`, add two new types of event `Panel Discussion, Party`, each type of event can be a vip event or a normal event.

    1.2 the organizer can decide how long one event last and assign each event a capacity. 

    1.3 event can be cancelled by one organizer and the Vip status of one event can be changed by organizer. (`Note` If you change the normal event to a vip event, it will kick users who are not vip)

    1.4 organizer now can change the setting including capacity, Vip status and speaker of the event. (`Note` When changing capacities, the new cap cannot be less than the number of signed up users, nor can it be larger than the room capacity)

2. We add waitList feature for all events. 
   
    2.1 User can signUp to the waitList if the signed up user for one event is equal to its capacity.

    2.2 The User will be automatically added to the event if there is someone who cancelled. 

    2.4 If you own a Vip account and you want to sign up for a waitList, 
          you will be added to the top of it (If there are some vip users already there, you will be added to the position behind them).

3. We did the database, the data is now updated immediately, and you can see new data once you re-click the menu button.

    3.1 Connect to the jedis database in the server so all the data can update real-timely and synchronize all the data.
      
    3.2 Gateway could serialize and deserialize the genetic data type in json format.
      
    3.3 Gateway is extendable for changing database, serialize/deserialize new type of objects.
      
    3.4 ADD a command line interface tool GatewayCli for debugging.

4. We changed the text UI to GUI and it can display 5 different languages.

`Note:` We have 7 members in our group and the total optional extensions we did counts as 4.


## Design Pattern <small><small>Group_0173</small></small>

### Entity
  *  There is a folder under entity named event factory. Class in that folder are involved in the implementation of the abstract factory design pattern
     
      *  We use Abstract Factory Design Pattern for different types of events (i.e. `NoneSpeakerEvent`, `OneSpeakerEvent`, `MultiSpeakerEvent`)
        The reason we decide to create these three subclasses inherit from Event and make precise Event type like `Part`, `Discussion Panel`, 
        `Talk` be subclasses of these three class is that it will be much easier to extend if we want to add more precise Event types
        (e.g. selfStudyEvent inherit from NonSpeakerEvent). And using Abstract Fanctory easily help us create each type of event.

### Controller

  - The main controller `ConferenceSystem` is made up of four helper classes, where `ConferenceSystem` calls methods in those subSystems (namely `ViewingSystem`, `MessagingSystem`, `EventEnrollmentSystem`, and `EventManagementSystem`). This is written with the Facade Design pattern in mind.
    - We grouped all controller methods into 5 categories.
      1) Anything related to the messaging experience in the application.
      2) Anything related to enrolling / disenrolling in events.
      3) Anything related to managing events, ie. changing details of the event, and adding new events, cancelling events.
      4) Anything related to viewing what are in the system. For instance, viewing all events currently in the conference.
      5) Anything else.
    - These 5 categories have different actors responsible for their functionality, or have similar functionality. 
    - With these methods separated, it ensures that the design doesn't violate single responsibility principle. Also, with these separated into different classes, we ensure that `Conference System` would not be as large of a class.
    - There are still methods that are hard coded in `ConferenceSystem` (namely those that fall in category 5), however we had the Facade design pattern in mind when design the controller.
  - Inheritence is introduced in the smaller helper classes. `subSystem` is an abstract class, which is the parent class of `ViewingSystem`, `MessagingSystem`, `EventEnrollmentSystem`, and `EventManagementSystem`.
    - This is to make the code more open-close, as further subSystems can be added without worrying about the 

### Presenter
  - There are two factories in the presenter package named PanelFactory and JOptionPaneFactory, which follows the Simple Factory Design Pattern.
    - The PanelFactory is used to make the JPanel that is placed in the content panel in the user menu. In this way, we can obscure the creation of the panel we use.
    - The JOptionPaneFactory, as its name suggests, is used to make the JOptionPane to tell user whether the action performed in the user menu is successful or not. In this way, we can
    also obscure the creation process of the JOptionPane we use. 
  - It is also worth noticing that we use the dependency Inversion Principle in the presenter, although the dependency inversion principle is not listed as a design pattern. 
  Since the presenter in the presenter package like PanelPresenter, Presenter, MessengerPresenter needs to update the view, but they can't depend on the view, 
  we let them depend on the abstraction, the interface (IUpdate or IMessage), so that we can follow the dependency principle in the Clean Architecture. 
    

### Gateway
  - Facade
    - `GatewayFacade` is a simple interface which integrates all different types of gateway as one.
  - Strategy
    - `SerializationStrategy` is strategy which it will change the serialization algorithm on different data types.
