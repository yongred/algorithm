<?php

class GasStation {

    /**
     * @param Integer[] $gas
     * @param Integer[] $cost
     * @return Integer
     */
    function canCompleteCircuit($gas, $cost)
    {
        $total = 0;
        $tank = 0;
        $start = 0;
        
        for ($i = 0; $i < sizeof($gas); $i++) {
            $total += ($gas[$i] - $cost[$i]);
            $tank += ($gas[$i] - $cost[$i]);
            if ($tank < 0) {
                $tank = 0;
                $start = $i + 1;
            }
        }
        
        return ($total < 0) ? -1 : $start;
    }
}