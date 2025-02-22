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
