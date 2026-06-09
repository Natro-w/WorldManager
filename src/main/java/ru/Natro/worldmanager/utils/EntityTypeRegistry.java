package ru.Natro.worldmanager.utils;

import java.util.HashMap;
import java.util.Map;

public class EntityTypeRegistry {

    private static final Map<Integer, String> TYPES = new HashMap<>();

    static {
        TYPES.put(10, "ANIMAL"); TYPES.put(11, "ANIMAL"); TYPES.put(12, "ANIMAL"); TYPES.put(13, "ANIMAL");
        TYPES.put(14, "ANIMAL"); TYPES.put(15, "ANIMAL"); TYPES.put(16, "ANIMAL"); TYPES.put(17, "ANIMAL");
        TYPES.put(18, "ANIMAL"); TYPES.put(19, "ANIMAL"); TYPES.put(22, "ANIMAL"); TYPES.put(23, "ANIMAL");
        TYPES.put(24, "ANIMAL"); TYPES.put(25, "ANIMAL"); TYPES.put(26, "ANIMAL"); TYPES.put(27, "ANIMAL");
        TYPES.put(28, "ANIMAL"); TYPES.put(29, "ANIMAL"); TYPES.put(30, "ANIMAL"); TYPES.put(31, "ANIMAL");
        TYPES.put(74, "ANIMAL"); TYPES.put(75, "ANIMAL"); TYPES.put(76, "ANIMAL"); TYPES.put(77, "ANIMAL");
        TYPES.put(78, "ANIMAL"); TYPES.put(79, "ANIMAL"); TYPES.put(80, "ANIMAL"); TYPES.put(81, "ANIMAL");
        TYPES.put(82, "ANIMAL"); TYPES.put(83, "ANIMAL"); TYPES.put(84, "ANIMAL"); TYPES.put(85, "ANIMAL");
        TYPES.put(86, "ANIMAL"); TYPES.put(87, "ANIMAL"); TYPES.put(88, "ANIMAL"); TYPES.put(89, "ANIMAL");
        TYPES.put(90, "ANIMAL"); TYPES.put(91, "ANIMAL"); TYPES.put(92, "ANIMAL"); TYPES.put(93, "ANIMAL");
        TYPES.put(94, "ANIMAL"); TYPES.put(95, "ANIMAL"); TYPES.put(96, "ANIMAL"); TYPES.put(97, "ANIMAL");
        TYPES.put(98, "ANIMAL"); TYPES.put(99, "ANIMAL"); TYPES.put(100, "ANIMAL"); TYPES.put(101, "ANIMAL");
        TYPES.put(102, "ANIMAL"); TYPES.put(103, "ANIMAL"); TYPES.put(104, "MOB"); TYPES.put(105, "MOB");
        TYPES.put(106, "MOB"); TYPES.put(107, "MOB"); TYPES.put(108, "MOB"); TYPES.put(109, "MOB");
        TYPES.put(110, "MOB"); TYPES.put(111, "MOB"); TYPES.put(112, "ANIMAL"); TYPES.put(113, "ANIMAL");
        TYPES.put(114, "MOB"); TYPES.put(115, "MOB"); TYPES.put(116, "ANIMAL"); TYPES.put(117, "ANIMAL");
        TYPES.put(118, "ANIMAL"); TYPES.put(119, "MOB"); TYPES.put(120, "ANIMAL"); TYPES.put(121, "ANIMAL");
        TYPES.put(122, "ANIMAL"); TYPES.put(123, "ANIMAL"); TYPES.put(124, "MOB"); TYPES.put(125, "ANIMAL");
        TYPES.put(126, "MOB"); TYPES.put(127, "MOB"); TYPES.put(128, "ANIMAL"); TYPES.put(129, "ANIMAL");
        TYPES.put(130, "ANIMAL"); TYPES.put(131, "MOB"); TYPES.put(132, "ANIMAL"); TYPES.put(133, "ANIMAL");
        TYPES.put(134, "ANIMAL");
        // Hostile mobs
        TYPES.put(32, "MOB"); TYPES.put(33, "MOB"); TYPES.put(34, "MOB"); TYPES.put(35, "MOB");
        TYPES.put(36, "MOB"); TYPES.put(37, "MOB"); TYPES.put(38, "MOB"); TYPES.put(39, "MOB");
        TYPES.put(40, "MOB"); TYPES.put(41, "MOB"); TYPES.put(42, "MOB"); TYPES.put(43, "MOB");
        TYPES.put(44, "MOB"); TYPES.put(45, "MOB"); TYPES.put(46, "MOB"); TYPES.put(47, "MOB");
        TYPES.put(48, "MOB"); TYPES.put(49, "MOB"); TYPES.put(50, "MOB"); TYPES.put(51, "MOB");
        TYPES.put(52, "MOB"); TYPES.put(53, "MOB"); TYPES.put(54, "MOB"); TYPES.put(55, "MOB");
        TYPES.put(56, "MOB"); TYPES.put(57, "MOB"); TYPES.put(58, "MOB"); TYPES.put(59, "MOB");
        TYPES.put(60, "MOB"); TYPES.put(61, "MOB"); TYPES.put(62, "MOB"); TYPES.put(63, "MOB");
        TYPES.put(64, "MOB"); TYPES.put(65, "MOB"); TYPES.put(66, "MOB"); TYPES.put(67, "MOB");
        TYPES.put(68, "MOB"); TYPES.put(69, "MOB"); TYPES.put(70, "MOB"); TYPES.put(71, "MOB");
        TYPES.put(72, "MOB"); TYPES.put(73, "MOB");
    }

    public static String getEntityType(int networkId) {
        return TYPES.getOrDefault(networkId, "MOB");
    }
}
