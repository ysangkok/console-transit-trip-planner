There are multiple applications:

* An IRC bot
* A Groovy script
* A Java program

To build the Java program with RoboVM, clone this repo and launch `make`.

Now you can find directions using e.g. `./javacmdlet/build/robovm/TripPlan "--time=5 hours from now" --provider=<provider> <departure-location> <destination-location>`. Because we use RoboVM, you do not need to wait for the JVM to launch, or have a daemon running.

All feedback is very welcome!

This is a sample of the output of the Groovy script. The Java output should be identical.

    $ ./go.sh --provider=Bahn "DA Taunusplatz" "Vladivostok"
    BUS H -> Anne-Frank-Straße
    Sun 00:08	Taunusplatz
    Sun 00:21	Darmstadt Hauptbahnhof
    Walk
    SUBURBAN_TRAIN S3 -> Niederhöchstadt
    Sun 00:35	Darmstadt Hbf
    Sun 01:02	Frankfurt(Main)Süd
    REGIONAL_TRAIN EN453 -> Moskva Belorusskaja
    Sun 03:16	Frankfurt(Main)Süd
    Mon 12:33	Moskva Belorusskaja
    Walk
    REGIONAL_TRAIN 710NJ -> Nishnij Novgorod Moskovsk
    Mon 15:40	Moskva Kurskaja
    Mon 19:30	Nishnij Novgorod Moskovsk
    REGIONAL_TRAIN D2MJ -> Vladivostok
    Mon 20:17	Nishnij Novgorod Moskovsk
    Sun 13:10	Vladivostok

See also
========

* https://github.com/derhuerst/vbb-cli (only for VBB [Berlin/Brandenburg])
* https://github.com/derf/Travel-Routing-DE-VRR

This Perl script works well if you need directions in an area that is covered by a system that uses EFA. As of 2015, its default provider doesn't know every stop in other cities:

```
$ efa Darmstadt Taunusplatz Frankfurt/Main Hauptbahnhof 
Backend error: The name 'Taunusplatz' is ambiguous. Try one of Hauptbahnhof | Arheilgen | Eberstadt | Kranichstein | Nordbahnhof | Ostbahnhof | Südbahnhof | Wixhausen | TU-Lichtwiese
```
