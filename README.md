# Assignment Hunter
# Team 26

Read Me:

Because the game rendering uses OpenGL, there are natives and external jar files that must
be present for the game to properly run. 
The executable jar file submitted is ensure to have the required jar files (such as lwjgl.jar)
and natives to allow the game to run on linux machines on the ECS computers. However,
if the game does not run due to certain reasons, another jar file has been submitted to
allow the game to be imported and run from Eclipse. 

The Name of our game is called: 'Assignment Hunter' and the aim of the game is to collect
the missing assignment pieces around the house. 
The assignment pieces are scattered around the house and can be found hidden inside items in the rooms.

When an assignment piece is found, it is automatically added to the player's container
like the other pickable items in the game.

To begin the game:
1. run the provided executable jar file 
2. you will be prompted to run as host or join, for the first time you must run as host
	and start the server.
3. To begin playing you must run the executable jar file again and join as host.
4. To run another player you must run the executable jar file and join as host.

/*****
NOTE: 
/*****
Because OpenGL natives are used and they differ depending on the OS the game is run on
there is a rare chance that the JVM may crash on the machine the game is tested on 
depending on the graphics card of the machine and other factors. 
We consulted David Pearce and we were not able to provide a proper solution to this 
problem and we have filed a bug report to Oracle on this matter. 

In the case of the JVM crashing, we advice you to ignore the crash and run the program again
and test each functionality of the game one at a time.
