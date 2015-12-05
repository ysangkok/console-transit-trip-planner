javacmdlet/build/robovm/TripPlan: javacmdlet/build/classes/main/T.class javacmdlet/build/classes/main/TripPlan.class
	gradle robovmInstall -PscriptArgs=bogus

javacmdlet/build/classes/main/TripPlan.class: javacmdlet/src/main/java/TripPlan.java
	gradle javacmdlet:clean javacmdlet:build -PscriptArgs=bogus

javacmdlet/build/classes/main/T.class: javacmdlet/src/main/java/T.java
	gradle javacmdlet:clean javacmdlet:build -PscriptArgs=bogus

javacmdlet/src/main/java/T.java: public-transport-enabler-master/.git
	./gen.sh > javacmdlet/src/main/java/T.java

public-transport-enabler-master/.git pircbotx/.git yaya-irc-bot/.git:
	git submodule init
	git submodule update
