import static java.lang.System.out;
import static java.lang.System.err;

import java.util.Locale;
import java.util.Date;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Set;

import de.schildbach.pte.dto.QueryTripsResult;
import de.schildbach.pte.dto.Location;
import de.schildbach.pte.NetworkProvider.Accessibility;
import de.schildbach.pte.NetworkProvider.WalkSpeed;
import de.schildbach.pte.dto.LocationType;
import de.schildbach.pte.dto.Product;
import de.schildbach.pte.dto.Trip;
import de.schildbach.pte.AbstractNetworkProvider;
import de.schildbach.pte.dto.SuggestLocationsResult;
import de.schildbach.pte.BahnProvider;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

public class TripPlan {
static void usage(){
  err.println("Syntax: TripPlan\n\t[--no-I Exclude high-speed trains]\n\t[--no-R Exclude regional trains]\n\t[--no-S Exclude suburban trains]\n\t[--no-U Exclude subway]\n\t[--no-T Exclude tram]\n\t[--no-B Exclude buses]\n\t[--no-F Exclude ferries]\n\t[--no-C Exclude cablecars]\n\t[--no-P Exclude on-demand transport (e.g. 'Anrufsammeltaxi')]\n\t[--nahverkehr Select all transportation methods except high-speed trains]\n\t[--provider=<provider>]\n\t[--time=<time>]\n\t<from>\n\t<to>");
  System.exit(1);
}
public static void main(String[] args) throws Exception {

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
    provider = T.t(i.substring("--provider=".length()));
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

SuggestLocationsResult sug1 = provider.suggestLocations(from);
SuggestLocationsResult sug2 = provider.suggestLocations(to);

QueryTripsResult result = provider.queryTrips(sug1.getLocations().get(0), null, sug2.getLocations().get(0), time, /*dep*/ true, products, null, WalkSpeed.NORMAL, Accessibility.NEUTRAL, null);

//println sprintf("From: %1$s, To: %2$s", result.from, result.to)

for (Trip trip : result.trips) {
	//println sprintf("From: %s, To: %s", [trip.from, trip.to])
        for (Trip.Leg leg : trip.legs) {
		if (!(leg instanceof Trip.Public)) {
			out.println("Walk");
			continue;
		}
		Trip.Public pleg = (Trip.Public) leg;
		out.format("%s %s -> %s\n", pleg.line.product, pleg.line.label, pleg.destination.name);
		out.format("%s\t%s\n", String.format(Locale.US, "%ta %<tR", pleg.departureStop.plannedDepartureTime), pleg.departureStop.location.name);
		out.format("%s\t%s\n", String.format(Locale.US, "%ta %<tR", pleg.arrivalStop.plannedArrivalTime), pleg.arrivalStop.location.name);
	}
	out.println("");
}

}
}
