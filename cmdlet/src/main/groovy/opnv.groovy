import de.schildbach.pte.dto.QueryTripsResult;
import de.schildbach.pte.dto.Location;
import de.schildbach.pte.NetworkProvider.Accessibility;
import de.schildbach.pte.NetworkProvider.WalkSpeed;
import de.schildbach.pte.dto.LocationType;
import de.schildbach.pte.dto.Product;

import de.schildbach.pte.*;

Set<Product> products = Product.ALL;
int currentArgument = 0;
String[] otherArgs = new String[3];

for (String i : args) {
  if (i.equals("--nahverkehr")) {
    products = EnumSet.complementOf(EnumSet.of(Product.HIGH_SPEED_TRAIN));
  } else if (i.length() == "--no-X".length() && i.startsWith("--no-")) {
    Set<Product> newSet = new HashSet<Product>(products);
    newSet.remove(Product.fromCode(i.charAt(i.length() - 1)));
    products = newSet;
  } else {
    otherArgs[currentArgument++] = i;
  }
}

if (otherArgs[2] == null) {
  println("Missing an argument!\nSyntax: TripPlan <provider> <from> <to>");
  exit(1);
}

provider = Class.forName("de.schildbach.pte." + otherArgs[0] + "Provider").newInstance()

sug1 = provider.suggestLocations(otherArgs[1])
sug2 = provider.suggestLocations(otherArgs[2])

result = provider.queryTrips(sug1.suggestedLocations[0].location, null, sug2.suggestedLocations[0].location, new Date(), /*dep*/ true, products, null, WalkSpeed.NORMAL, Accessibility.NEUTRAL, null)

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
