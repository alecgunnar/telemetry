package sunseeker.telemetry.data.parser;

import org.junit.Before;
import org.junit.Test;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by aleccarpenter on 6/24/17.
 */
public class GenericParserTest {
    private GenericParser subject;

    private String singleLineOfSampleData = "ABCDEF\n" +
            "ABC,0x00000000,0xA7C3F942\n" +
            "UVWXYZ";

    private String firstHalfOfSampleData = "ABCDEF\n" +
            "DEF,0x00000000,0xA7C3F942\n";

    private String secondHalfOfSampleData = "GHI,0x00000000,0xA7C3F942\n" +
            "UVWXYZ";

    private Map<String, Double> result;

    @Before
    public void setup() {
        subject = new GenericParser();

        result = subject.pushData(singleLineOfSampleData);
    }

    @Test
    public void should_returnAMapWithKeyZero_whenDataIsPushed() {
        assertThat(result.containsKey("ABC_0"), is(true));
    }

    @Test
    public void should_returnAMapWithKeyOne_whenDataIsPushed() {
        assertThat(result.containsKey("ABC_1"), is(true));
    }

    @Test
    public void should_mapTheFirstValueToKeyZero_whenDataIsPushed() {
        assertThat(result.get("ABC_0"), is(0.0));
    }

    @Test
    public void should_mapTheSecondValueToKeyOne_whenDataIsPushed() {
        // TODO: make this assertion less fragile
        assertThat(result.get("ABC_1"), is(124.88213348388672));
    }

    @Test
    public void should_returnsFirstHalfOfData_givenSplitPage_whenDataIsPushed() {
        Map<String, Double> result;

        result = subject.pushData(firstHalfOfSampleData);

        assertTrue(
                result.containsKey("DEF_0")
                        && result.containsKey("DEF_1")
        );
    }

    @Test
    public void should_returnsSecondHalfOfData_givenSplitPage_whenDataIsPushed() {
        result = subject.pushData(secondHalfOfSampleData);

        assertTrue(
                result.containsKey("GHI_0")
                && result.containsKey("GHI_1")
        );
    }
}