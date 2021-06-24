/*
 * https://www.comrevo.com/2019/07/Sending-objects-over-sockets-Java-example-How-to-send-serialized-object-over-network-in-Java.html
 */

package ExamplesAndReferences;

import java.io.Serializable;

public class Sample implements Serializable
{
    String name;
    String city;
    String contactnum;
}
