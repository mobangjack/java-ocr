To build with Maven:

   mvn clean install

Attention:  This library in under heavy refactoring right now -
you may have difficulty to recognise it next day


Module overview

core - core image processing stuff (images over linear arrays, some basic filters,
        image slicing and traversal code)
plugins - separated plugin modules (awt - utilises AWT routines for image processing,
fir - FIR filtering, moment - invariant moments computation)
tools - SWING Applications and demonstrators,  currently in bad shape

Android support

All modulles not utlising AWT are suitable for use on android

To create an Eclipse 3.6 project for the android demo:
File -> New -> Android project
	Create project from existing source
	Location: browse to trunk/demos/android
	select a build target
In Package Explorer, Right-click the project -> Properties
	Java build path -> Source
	Link Source: Browse to trunk/core/src/main/java ; for Folder Name, enter javaocr-core
	Link Source: Browse to trunk/plugins/moment/src/main/java ; for Folder Name, enter javaocr-plugin-moment
Remove trunk/core/src/main/java/net/sourceforge/javaocr/scanner/*.java, these deprecated classes are not Android-compatible
In Package Explorer, right-click the project -> Android tools -> Fix project properties
Project -> Clean -> OcrDemo (or your project name)
Now, you should be able to right-click the project and Run as an Android Application.
