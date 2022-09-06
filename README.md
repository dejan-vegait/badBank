# Fix and refactor

##  Simple bank 

The application is built using 2 classes. 

Account class models how much money a person has. Two main methods are
deposit and withdraw. Account may never have negative deposit. (There is an if statement preventing that).
Bank class models registering account and getting existing account (by id). 

Both classes have a lot of bugs.

## Simulation

There is a 3rd class BankRunner which has a main method.  
This class runs a simulation, where some number of accounts is created.
Then some random transfers are done. This is performed using multiple threads.  
After that the overall amount of money on accounts should not change.

This seems not to be the case.

##  Task

Your job is to find bugs and other problems in the implementation.
1. Make corrections to the Account and Bank classes. (if you do not have time, please simply list the problems that you see).
2. You may also have to change BankRunner class! In such case, please try to preserve its intended logic.
3. Additionally try to fix or list other potential code quality issues that you see.
4. You are free to create additional classes or files as needed. 

## Comments - from refactoring process
1. I usually do not add javadoc (unless is for interfaces/API or when names are not self-explaining), 
but I've documented public methods within Bank and Account classes.
2. I usually do not add formatter plugin, since that depends on team/project. So, if needed it could be added following plugin:
   <plugin>
       <groupId>net.revelc.code.formatter</groupId>
       <artifactId>formatter-maven-plugin</artifactId>
       <version>2.19.0</version>
   </plugin>
3. .gitignore was added based on content that I've "collected" for years by multiple people/projects. 
For this project it was not needed to be that huge (just couple of entries was really needed).
4. System.out.println() was replaced with logger, since even for this kind of test project, it is a bed practise.
5. As thrown exceptions, it was used IllegalStateException. One additional update might be to create custom exceptions.
6. Usually any work in git I do in a separate branch; feature or bugfix, but at this time I decided to commit directly to master.