package no.nordicsemi.android.meshprovisioner.utils;

import android.util.Log;

import java.util.ArrayList;

/**
 * Output OOB Actions
 */
@SuppressWarnings("unused")
public enum OutputOOBAction {

    /**
     * Output OOB Actions
     */
    NO_OUTPUT((short) 0x0000),
    BLINK((short) 0x0001),
    BEEP((short) 0x0002),
    VIBRATE((short) 0x0004),
    OUTPUT_NUMERIC((short) 0x0008),
    OUTPUT_ALPHA_NUMERIC((short) 0x0010);

    private static final String TAG = OutputOOBAction.class.getSimpleName();
    private short outputOOBAction;

    OutputOOBAction(final short outputOOBAction) {
        this.outputOOBAction = outputOOBAction;
    }

    public short getOutputOOBAction() {
        return outputOOBAction;
    }

    /**
     * Returns the oob method used for authentication
     *
     * @param method auth method used
     */
    public static OutputOOBAction fromValue(final short method) {
        switch (method) {
            default:
            case 0x0000:
                return NO_OUTPUT;
            case 0x0001:
                return BLINK;
            case 0x0002:
                return VIBRATE;
            case 0x0004:
                return VIBRATE;
            case 0x0008:
                return OUTPUT_NUMERIC;
            case 0x0010:
                return OUTPUT_ALPHA_NUMERIC;
        }
    }

    /**
     * Returns the Output OOB Action description
     *
     * @param type Output OOB type
     * @return Input OOB type descrption
     */
    public static String getOutputOOBActionDescription(final OutputOOBAction type) {
        switch (type) {
            case NO_OUTPUT:
                return "Not Supported";
            case BLINK:
                return "Blink";
            case BEEP:
                return "Beep";
            case VIBRATE:
                return "Vibrate";
            case OUTPUT_NUMERIC:
                return "Output Numeric";
            case OUTPUT_ALPHA_NUMERIC:
                return "Output Alpha Numeric";
            default:
                return "Unknown";
        }
    }

    /**
     * Parses the Output OOB Action
     *
     * @param type output OOB type
     * @return Output OOB type descrption
     */
    public static int parseOutputOOBActionValue(final OutputOOBAction type) {
        switch (type) {
            case NO_OUTPUT:
                return 0;
            case BLINK:
                return 1;
            case BEEP:
                return 2;
            case VIBRATE:
                return 3;
            case OUTPUT_NUMERIC:
                return 4;
            case OUTPUT_ALPHA_NUMERIC:
                return 10;
            default:
                return -1;
        }
    }

    /**
     * Parses the output oob action value
     *
     * @param outputAction type of output action
     * @return selected output action type
     */
    public static ArrayList<OutputOOBAction> parseOutputActionsFromBitMask(final int outputAction) {
        //final byte[] outputActions = {(byte) BLINK.ordinal(), (byte) BEEP.ordinal(), (byte) VIBRATE.ordinal(), (byte) OUTPUT_NUMERIC.ordinal(), (byte) OUTPUT_ALPHA_NUMERIC.ordinal()};
        final OutputOOBAction[] outputActions = {BLINK, BEEP, VIBRATE, OUTPUT_NUMERIC, OUTPUT_ALPHA_NUMERIC};
        final ArrayList<OutputOOBAction> supportedActionValues = new ArrayList<>();
        for(OutputOOBAction action : outputActions){
            if((outputAction & action.outputOOBAction) == action.outputOOBAction){
                supportedActionValues.add(action);
                Log.v(TAG, "Supported output oob action type: " + getOutputOOBActionDescription(action));
            }
        }
        return supportedActionValues;
    }

    /**
     * Selects the output oob action value
     *
     * @param outputAction type of output action
     * @return selected output action type
     */
    public static OutputOOBAction selectOutputActionsFromBitMask(final OutputOOBAction outputAction) {
        //final byte[] outputActions = {BLINK, BEEP, VIBRATE, OUTPUT_NUMERIC, OUTPUT_ALPHA_NUMERIC};
        final OutputOOBAction[] outputActions = {BLINK, BEEP, VIBRATE, OUTPUT_NUMERIC, OUTPUT_ALPHA_NUMERIC};
        final ArrayList<OutputOOBAction> supportedActionValues = new ArrayList<>();
        for(OutputOOBAction action : outputActions){
            if((outputAction.ordinal() & action.outputOOBAction) == action.outputOOBAction){
                supportedActionValues.add(action);
                Log.v(TAG, "Supported output oob action type: " + getOutputOOBActionDescription(action));
            }
        }

        if(!supportedActionValues.isEmpty()) {
            return supportedActionValues.get(0);
        } else {
            return NO_OUTPUT;
        }
    }

    /**
     * Returns the Output OOB Action
     *
     * @param type output OOB type
     * @return Output OOB type descrption
     */
    public static int getOutputOOBActionValue(final OutputOOBAction type) {
        switch (type) {
            case BLINK:
                return 0;
            case BEEP:
                return 1;
            case VIBRATE:
                return 2;
            case OUTPUT_NUMERIC:
                return 3;
            case OUTPUT_ALPHA_NUMERIC:
                return 4;
            default:
                return 0;
        }
    }

    public static byte[] generateOutputOOBAuthenticationValue(final OutputOOBAction outputActionType, final byte[] input, final byte outputOOBSize){
        switch (outputActionType) {
            case BLINK:
            case BEEP:
            case VIBRATE:
            case OUTPUT_NUMERIC:
                return MeshParserUtils.createAuthenticationValue(true, input, outputOOBSize);
            case OUTPUT_ALPHA_NUMERIC:
                return MeshParserUtils.createAuthenticationValue(false, input, outputOOBSize);
            default:
                return null;
        }
    }
}