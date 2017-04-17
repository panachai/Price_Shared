package com.panachai.priceshared;

import com.squareup.otto.Bus;

/**
 * Created by KILLERCON on 17/4/2560.
 */


public final class BusProvider {

    private static Bus bus;

    public static Bus getInstance() {
        if (bus == null) {
            bus = new Bus();
        }

        return bus;

    }
}
