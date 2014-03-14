Demo code for the Timehop team
==========================
Hello Jonathan and / or other Timehop team members! This repository contains sample code written by Alexey Dmitriev. Please review it to decide whether my skills and style are appropriate for your team.

This repository is a pack of code excerpts from several different projects. Thus it is organized in folders rather than usual Java packages; please also note that some paragraphs of code are omitted due to NDA obligations.

There was no agenda on which code to choose so I decided to demonstrate how I use Design Patterns in a daily life, because I love to have clean and structured code and I always do my best to reach this goal.

Comments on the folders
-----------------------
### fragmentBuilders

In Android, most screens are represented by Fragments. Many of Fragments typically contain a list view, an engine to load data into that view, an event handler which fires when user taps on item in that list.

To facilitate and unify a way of creating such family of Fragments, I used the Builder pattern.

### fragmentForms

Fragments which contain forms helping to create or edit, for example, child or classroom record have many steps in common: both need to check whether all necessary fields are entered; both need to check that a record with the same data does not already exist, etc.

To encapsulate this common behaviour, I used the Template Method pattern.

### kitchen

Meal serving data for one of the projects needs to be displayed in a complex table. I decided to stick to the Composite pattern: individual cells are leaves, rows and tables are branches in such a tree structure.

Several other patterns are also present in this folder: the Factory, to encapsulate creation of cells; the Decorator, to add functionality to a table so it can be displayed in edit mode, in a pop-up dialog; the Adapter, which adapts a generic textual cell so it can be used in a table for which meal component aware cells are needed.

### misc

Bubble manager handles asynchronous requests to display bubble messages on screen. It also demonstrates usage of the Singleton pattern.

Change Observer provides a way to notify interested parties that data has changed; it features loose coupling with its subscribers to make sure no memory leak would occur. It also demonstrates the Observer pattern.

### model

Contains a POJO object which is a subject to the Memento pattern. This helps user to undo changes he can make to a meal component picked at previous step.

### monitors

In this example, monitor is a way to track state of objects of a particular class. The implementation supports different expiration policies, for example, monitor can be set to expire in one day.

### requests

Objects of many different types have to be uploaded or downloaded from server. This implementation of REST requests knows how to automatically convert objects to / from JSON. 

### widgets

Contains an implementation of custom list. Every odd row of this list contains a single item, every even row - two items. Both rows have the same width.


Thank you for your attention!