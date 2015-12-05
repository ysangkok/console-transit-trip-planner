import de.schildbach.pte.dto.QueryTripsResult;
import de.schildbach.pte.dto.Location;
import de.schildbach.pte.NetworkProvider.Accessibility;
import de.schildbach.pte.NetworkProvider.WalkSpeed;
import de.schildbach.pte.dto.LocationType;
import de.schildbach.pte.dto.Product;

import de.schildbach.pte.*;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

void usage() {
  println "Incorrect parameters"
  System.exit 1
}

Set<Product> products = Product.ALL;
int currentArgument = 0;
AbstractNetworkProvider provider = new BahnProvider();
Date time = new Date();
String to = null;
String from = null;

for (String i : args) {
  if (i.equals("--nahverkehr")) {
    products = EnumSet.complementOf(EnumSet.of(Product.HIGH_SPEED_TRAIN));
  } else if (i.length() == "--no-X".length() && i.startsWith("--no-")) {
    Set<Product> newSet = new HashSet<Product>(products);
    newSet.remove(Product.fromCode(i.charAt(i.length() - 1)));
    products = newSet;
  } else if (i.startsWith("--provider=")) {
    provider = Class.forName("de.schildbach.pte." + i.substring("--provider=".length()) + "Provider").newInstance()
  } else if (i.startsWith("--time=")) {
    time = new PrettyTimeParser().parse(i.substring("--time=".length())).get(0);
  } else if (i.startsWith("--")) {
    err.println("Invalid flag.");
    usage();
  } else if (currentArgument == 0) {
    from = i;
    currentArgument++;
  } else if (currentArgument == 1) {
    to = i;
    currentArgument++;
  } else {
    err.println("Too many arguments.");
    usage();
  }
}

if (to == null || from == null) {
  err.println("Missing an argument.");
  usage();
}

sug1 = provider.suggestLocations(from);
sug2 = provider.suggestLocations(to);

result = provider.queryTrips(sug1.suggestedLocations[0].location, null, sug2.suggestedLocations[0].location, time, /*dep*/ true, products, null, WalkSpeed.NORMAL, Accessibility.NEUTRAL, null)

//println sprintf("From: %1$s, To: %2$s", result.from, result.to)

for (trip in result.trips) {
	//println sprintf("From: %s, To: %s", [trip.from, trip.to])
        for (leg in trip.legs) {
		if (leg.getClass().getSimpleName() == "Individual") {
			println "Walk"
			continue
		}
		println sprintf("%s %s -> %s", [leg.line.product, leg.line.label, leg.destination.name])
		println sprintf("%s\t%s", [String.format(Locale.US, "%ta %<tR", leg.departureStop.departureTime), leg.departureStop.location.name])
		println sprintf("%s\t%s", [String.format(Locale.US, "%ta %<tR", leg.arrivalStop.plannedArrivalTime), leg.arrivalStop.location.name])
	}
	println()
}
