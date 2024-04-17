
# Project Phase 1

Specification

You will create a program that allows people at a conference (examples: a tech conference, a TED-style conference, an employment fair, etc.) to communicate with each other in very specific ways. In Phase 0, you already designed a program that will allow Attendees to sign up for events and message each other. Now, you will change the class names to something more applicable so that the same design will become part of your Phase 1 submission. Alternatively, you can design Phase 1 independently of your Phase 0 ideas.

By the end of Phase 2, you will have an app that allows Organizers of a conference to plan the event, allows Speakers at the event to learn the information they need to speak and communicate with the Attendees of their presentation, allows Attendees to sign up for events and network with each other, and more.

For Phase 1, your User Interface (UI) will be text only. This involves printing menu items and user prompts on the screen and reading the user's response from the keyboard.

The objective of Phase 1 is to allow you to try out the SOLID principles of design and the clean architecture organization. In Phase 2, you will be asked to extend your program in various ways. This will give you a sense of how much your program followed these principles. Keep in mind possible extension that you might want to implement in Phase 2.

If your mark is higher on Phase 2 than on Phase 1, we will replace your Phase 1 mark with your Phase 2 mark. In order to receive a passing grade on Phase 1, you must submit a program that compiles and runs.

Users

Organizers of the conference should be able to enter rooms into the system and create speaker accounts. They should also be able to schedule the speakers to each speak in one or more rooms at different times so that at most one speaker speaks in each room at any given time and each room has at most one speaker at any given time. You can assume that all talks are 1 hour long and happen between 9 am and 5 pm. This will change for Phase 2. Also, in Phase 2, the Organizer will have the ability to cancel or reschedule an event before it happens. You may want to consider this when you create your initial design.

Speakers at the conference should be able to log in and see a list of talks that they are giving. They should also be able to message all Attendees who are signed up for a particular event, at once or individually.

Attendees should be able to log in and see a schedule of events for which they can sign up. They should be able to sign up for events, cancel their enrolment in an event, see the schedule of the events for which they signed up, send messages to and receive messages from other Attendees, and message with Speakers.

Login System

Every user should be able to log into the system and interact with a menu of options that are specific to their type of user.

Schedule System

It should be possible for an Organizer to schedule each speaker to give a talk at specific times in specific rooms, without double-booking a speaker (scheduling them to speak two places at the same time) or double-booking a room (scheduling two people to speak in the same room at the same time). This can be done automatically by your system, by giving the user enough information to avoid such conflicts, or restricting the choices an Organizer can make.

Sign-Up System

Attendees will be able to browse the talks and decide which ones they want to see. They will also be able to sign up for the talk. You can assume that each room has a capacity of 2 people besides the speaker. For Phase 2, this will change. But for now, it makes it easier to test your program. Attendees should not be able to sign up for an event that is already full.

Messaging System

Speakers should be able to send a message that automatically goes to all Attendees of their talk or multiple talks that they gave.

Organizers should be able to send a message to all speakers, one specific speaker, all Attendees, or one specific Attendee.

Attendees should be able to message other Attendees or Speakers. Speakers should be able to respond to a specific Attendee.

Saving and Persistence of Information

When your program finishes running, information inside the program will be deleted. If you want to be able to use that information the next time you run your program, you will have to store that info outside of your program and have your program read it back in. Good ways to do this are by storing information in a .txt file, a .csv file, or a .ser file.

Do not use a database for Phase 1. If you know how to connect your program with a database, you can do this for Phase 2 if you want. But we will only mark the way it interacts with your program and not the data base itself. So including a database in your Phase 2 submission will not earn you extra marks.

What You Should Submit

Your code, including Javadoc Preview the documentfor anything that is not private.
Any configuration files that are needed to run your program
a readme.txt file that contains any instructions we need in order to run your program and try out the required functionality. The readme.txt file should be located directly inside the phase1 folder in your group repository
a design.pdf file that contains a uml diagram of your program. If it is too large and complicated, you are welcome to create a series of diagrams in files called design1.pdf, design2.pdf, etc. that contain uml diagrams for different parts of your program. These files should also be directly inside your phase1 folder.
Getting Started

Designate one group member to do the following:

Log into MarkUs and click on the phase1 repository url. This will either do nothing or result in an error message. This is fine. The act of clicking on the link will create the repository. Copy the link.
Clone your repository, using that link. The folder name for your repository should be group_0XXX where the X's should be replaced by your group number. You can find your group number from your repo's url.
In IntelliJ, if you have a project open, go to the "File" menu and select "close project".
From the welcome screen, choose "Create New Project" and make sure that it is located inside your phase1 folder in your group repository.
Add, commit, and push this new project.
Everyone else in your group should clone the repository. The new project should exist in everyone else's copy of the phase1 folder. Make sure that any classes or interfaces that you create go in the "phase1/src" folder.

Tests
Test Driven Development is a method for coding where you decide what each method should do before implementing it. Then you write tests to make sure each method works. As you implement the methods, you can run them against the tests that you created to make sure they do what you intended. This is optional, but an excellent way to get started, after you finish your CRC cards.

Feel free to ask questions about the project before/after lecture, during office hours, on the message board (Links to an external site.) (Links to an external site.), or you can request an appointment through the Office Hours page. When you ask a question on the message board, the entire class can benefit from hearing the answer. If your question involves discussing your code, an appointment is better, so that you do not get accused of leaving your code for others to plagiarize. Any subset of your group, from one member to everyone, is welcome to attend an appointment.

# Project Phase 2
How to start Phase 2 <-- Setup Instructions and Tips

The primary goal of Phase 2 is to give you a chance to see how extensible your code is, by extending it! There are three categories of extensions that you will make for Phase 2, before submitting your code and accompanying documentation. Here they are:

1. Mandatory extensions
There will now be many types of events. A one-speaker event is the same as a "talk" from Phase 1. You can have multi-speaker events, like a panel discussion, and no-speaker events, like a party. Events can last as long as you want. You can measure the duration of an event in hours, or minutes. You get to decide.
Events can be canceled by at least one organizer.
At least one more type of user will be included in your program. For example, an Admin user who can delete messages or events with no attendees, or a VIP user who can access VIP-only events.
Organizers can also create Speaker accounts, Attendee accounts, and any other type of user accounts that are in your program.
Each event has a maximum number of people who can attend it. This amount can be set when the event is created and also changed later, by an organizer. Your program should check the maximum capacity for the room where the event will be held, and prevent the number of attendees from going above the room's capacity.
2. Optional Extensions (Choose 4*)
You will implement four of these, unless you implement a GUI. Please see the note below the list. Also, if your group has fewer than 7 members, please see an instructor during an office hour or after lecture to discuss how many of the features in this section you are required to implement.

Allow the same users to log in and select which conference they want to participate in. Here, participation means viewing and signing up for events. The inbox can be conference-specific, or one general inbox for all messages from all conferences to that user. You decide which one.
Enhance the user's messaging experience by allowing them to "mark as unread", delete, or archive messages after reading them.
Have the program produce a neatly formatted program or schedule for the conference that users have the option of "downloading" (outputting it as html, pdf, or similar). Alternatively, if you just want the program to print the schedule to the screen, then users should be able to request a schedule by at least three of: day, by speaker, by time (all 3-4 pm talks on all days), or just the ones they have signed up for, or "liked" events (where you have to enable users to "like" events).
Add additional constraints to the scheduling for various types of events (e.g. technology requirements for presentations, tables vs rows of chairs). When organizers are creating events, they can see a suggested list of rooms that fit the requirements of their event.
Allow the system to support additional user requests (e.g. dietary restrictions, accessibility requirements) where organizers can tag a request as "pending" or "addressed". All organizers see the same list of requests, so if one tags a request as addressed, all other organizers can log in and see that.
Use a database to store the information from your program through specific gateway class(es) so that you do not violate Clean Architecture and still have an Entity layer.
Expand the menus available to organizers to allow them to get useful summary stats about the conference. Include app traffic statistics, event enrollment statistics, and top-five lists (or something similar). You should include enough statistics to make this option as complicated as the other ones on this list.
Replace your text UI with a Graphic User Interface (GUI), which should follow the Model-View-Presenter architecture. See the note below.
*Note: Implementing a GUI with JavaFx, java.swing, java.awt, or Android counts as two features. We will not be teaching GUIs in this course, so you will have to refer to online tutorials and examples.

3. Create your own new features
You are encouraged to invent new features that you would want to use in this program. The purpose of these invented features is to give you something to brag about during job interviews and make your program a bit different than everyone else's in the class.

These features must be as complicated as the mandatory features, listed above. You can do this by implementing one big new feature or several smaller ones. If you are having difficulty thinking of new features, you can discuss this part with your TA, during office hours, or just implement more of the Optional (Type 2) Features.

4. What you will submit
The code
A README file with instructions for running your program and any other information we need to know to test its functionality.
A list of features that you implemented for Phase 2, so we don't miss any.
(optional) A video showing how your program works (and that it works). This is non-optional if we are required to install anything in order to run your program. In the absence of a video, you are expected to demonstrate how your code works to your TA during your last weekly meeting.
A single UML diagram for the entire program or partial diagrams for different parts of your program. The single file should be called design.pdf. If you use multiple files, you can call them design1.pdf, design2.pdf, etc.
A list of design patterns that you used and why/how. Please include the names of all classes involved in the implementation of the pattern. The goal is to convince us that you understand the course material.
You can also include reasons for not using certain design patterns, but that part is optional.
(optional) A list of design decisions and explanations about how your code has improved since Phase 1.
It is possible that you will receive an email from your TA during the 10 days immediately after the submission deadline with requests to meet with individual group members and/or the entire group. The email will include the questions that the TA will ask you, so you can prepare for the meeting. We will only email you if we need your help to assign a fair grade to each group member. The group mark is based on the code. Your individual mark will only be the same as the group mark if we can find evidence in the git logs that you contributed approximately equally to the coding of your project. This does NOT mean that you should commit every few minutes. We can see what changes were made inside each commit. Many small commits will be counted as a few larger commits. It DOES mean that you should contribute code and not just Javadoc or just move existing code around.