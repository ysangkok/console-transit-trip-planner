import static java.lang.System.out;
import java.util.Locale;
import java.util.Date;

import de.schildbach.pte.dto.QueryTripsResult;
import de.schildbach.pte.dto.Location;
import de.schildbach.pte.NetworkProvider.Accessibility;
import de.schildbach.pte.NetworkProvider.WalkSpeed;
import de.schildbach.pte.dto.LocationType;
import de.schildbach.pte.dto.Product;
import de.schildbach.pte.dto.Trip;
import de.schildbach.pte.AbstractNetworkProvider;
import de.schildbach.pte.dto.SuggestLocationsResult;

public class TripPlan {
public static void main(String[] args) throws Exception {

AbstractNetworkProvider provider = T.t(args[0]);

SuggestLocationsResult sug1 = provider.suggestLocations(args[1]);
SuggestLocationsResult sug2 = provider.suggestLocations(args[2]);

QueryTripsResult result = provider.queryTrips(sug1.getLocations().get(0), null, sug2.getLocations().get(0), new Date(), /*dep*/ true, Product.ALL, null, WalkSpeed.NORMAL, Accessibility.NEUTRAL, null);

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