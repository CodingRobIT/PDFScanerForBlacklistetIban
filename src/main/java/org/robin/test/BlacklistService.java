package org.robin.test;

import java.util.List;

public class BlacklistService {

    /**
     * TODO: In a larger project, the blacklist should be loaded from an external file or database,
     *       instead of saving it directly in the code. This keeps the list flexible and easy to update.
     */
    public List<String> getBlacklistedIbans() {
        return List.of(
                "DE15 3006 0601 0505 7807 80", /* This Iban is in the Test PDF just delete the number to get an 200 Respond*/
                "DE00 0000 0000 0000 0000 00"
        );
    }
}
