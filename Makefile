javacmdlet/build/robovm/TripPlan:
	gradle robovmInstall -pscriptArgs=bogus

javacmdlet/src/main/java/T.java:
	./gen.sh > javacmdlet/src/main/java/T.java

public-transport-enabler-master/.git pircbotx/.git yaya-irc-bot/.git:
	git submodule init
	git submodule update
