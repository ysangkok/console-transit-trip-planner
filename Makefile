javacmdlet/build/robovm/TripPlan: javacmdlet/build/classes/main
	gradle robovmInstall -PscriptArgs=bogus

javacmdlet/build/classes/main: javacmdlet/src/main/java/T.java public-transport-enabler-master/.git
	gradle javacmdlet:build -PscriptArgs=bogus

javacmdlet/src/main/java/T.java:
	./gen.sh > javacmdlet/src/main/java/T.java

public-transport-enabler-master/.git pircbotx/.git yaya-irc-bot/.git:
	git submodule init
	git submodule update
