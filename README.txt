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
