###Feature List:
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