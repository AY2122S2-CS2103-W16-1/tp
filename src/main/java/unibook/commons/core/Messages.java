package unibook.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_MODULE_DISPLAYED_INDEX = "The module index provided is invalid";
    public static final String MESSAGE_INVALID_GROUP_DISPLAYED_INDEX = "The group index provided is invalid";
    public static final String MESSAGE_INVALID_MEETING_DISPLAYED_INDEX = "The meeting time index provided is invalid";
    public static final String MESSAGE_INVALID_PROF_DISPLAYED_INDEX = "The professor index provided is invalid";
    public static final String MESSAGE_INVALID_STU_DISPLAYED_INDEX = "The student index provided is invalid";
    public static final String MESSAGE_INVALID_KEY_EVENT_DISPLAYED_INDEX = "The key event index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_CHANGE_TO_MODULE_PAGE = "Change page to Module Page!\n"
            + "Command to change to module page: list o/view v/modules";
    public static final String MESSAGE_CHANGE_TO_PERSON_PAGE = "Change page to Person Page!\n"
            + "Command to change to person page: list o/view v/people";
    public static final String MESSAGE_CHANGE_TO_GROUP_PAGE = "Change page to Group Page!\n"
            + "Command to change to group page: list o/view v/groups";
    public static final String MESSAGE_CHANGE_TO_MODULE_OR_GROUP_PAGE = "Change page to Module Page or Group Page!\n"
            + "Command to change to module page: list o/view v/modules\n"
            + "Command to change to group page: list o/view v/groups";
    public static final String MESSAGE_MODULE_CODE_NOT_EXIST = "Module Code does not exist: %1$s";
    public static final String MESSAGE_GROUP_NOT_EXIST = "Group does not exist: %1$s";

    //ListCommand
    public static final String MESSAGE_CHANGED_TO_MODULE_PAGE = "Changed page to Module Page!";
    public static final String MESSAGE_CHANGED_TO_PERSON_PAGE = "Changed page to Person Page!";
    public static final String MESSAGE_CHANGED_TO_GROUP_PAGE = "Changed page to Group Page!";
    public static final String MESSAGE_ALREADY_ON_PEOPLE_PAGE = "You are already on the People view!";
    public static final String MESSAGE_ALREADY_ON_MODULE_PAGE = "You are already on the Modules view!";
    public static final String MESSAGE_ALREADY_ON_GROUP_PAGE = "You are already on the Groups view!";
    public static final String MESSAGE_LISTED_PEOPLE_WITH_MODULE = "Listed all people in module %s!";
    public static final String MESSAGE_LISTED_MODULE = "Listed module %s!";
    public static final String MESSAGE_LISTED_ALL_STUDENTS = "Listed all students!";
    public static final String MESSAGE_LISTED_ALL_PROFESSORS = "Listed all professors!";
    public static final String MESSAGE_LISTED_ALL_TYPE_IN_MODULE = "Listed all %s in module %s!";
    public static final String MESSAGE_LISTED_MODULE_GROUP = "Listed group %s in module %s!";
    public static final String MESSAGE_LISTED_GROUP_WITH_NAME = "Listed groups with name %s!";
    public static final String MESSAGE_WRONG_VIEW = "The command requires you to switch to the %s view.";
    public static final String MESSAGE_WRONG_TYPE = "The type field must be professors or students.";
    public static final String MESSAGE_INVALID_VIEW = "The view field must be modules or people.\n";
    public static final String MESSAGE_INVALID_LIST_OPTION = "The option field must be view, module, or group.";
    public static final String MESSAGE_MODULE_FIELD_MISSING = "The m/<MODULECODE> field is missing!";
    public static final String MESSAGE_TYPE_FIELD_MISSING = "The ty/<TYPE> field is missing!";
    public static final String MESSAGE_GROUP_FIELD_MISSING = "The g/<GROUPNAME> field is missing!";
    public static final String MESSAGE_VIEW_FIELD_MISSING = "The v/<VIEW> field is missing!";
    public static final String MESSAGE_GROUP_NOT_IN_MODULE = "The group %s does not exist in the module!";

    public static final String MESSAGE_GROUP_NOT_IN_UNIBOOK = "The group %s does not exist in Unibook!";
    public static final String MESSAGE_DISPLAYED_GROUPS_WITH_NAME = "Displayed group(s) with name %s!";
    public static final String MESSAGE_DISPLAYED_PEOPLE_IN_GROUP = "Displayed people in %s %s!";
    public static final String MESSAGE_NO_GROUP_WITH_MEETING_DATE = "No groups have meetings on %s!";
    public static final String MESSAGE_DISPLAYED_GROUPS_WITH_MEETING_DATE = "Displayed groups with meeting(s) on %s!";
    public static final String MESSAGE_DISPLAYED_MODULES_WITH_NAME = "Displayed modules with %s in their name!";
    public static final String MESSAGE_DISPLAYED_MODULES_WITH_KEY_EVENT = "Displayed modules with key event(s) %s!";
    public static final String MESSAGE_DISPLAYED_MODULES_WITH_DATE = "Displayed modules with key event(s) on %s!";
    public static final String MESSAGE_NO_MODULES_WITH_DATE = "No modules have key events on %s!";
    public static final String MESSAGE_NO_MODULES_WITH_NAME = "No modules have names containing %s!";
    public static final String MESSAGE_INVALID_KEY_EVENT = "Invalid Key Event: %s. Acceptable arguments are "
        + "EXAM, ASSIGNMENT_DUE, ASSIGNMENT_RELEASE or QUIZ.";
    public static final String MESSAGE_DISPLAYED_MODULES_WITH_EVENT_AND_DATE =
        "Displayed modules with key event %s on %s!";
    public static final String MESSAGE_NO_MODULE_WITH_EVENT_AND_DATE =
        "No modules have key event(s) %s on %s!";
    public static final String MESSAGE_NO_MODULE_WITH_NAME_AND_DATE =
        "No modules have module names containing %s with key event(s) on %s!";
    public static final String MESSAGE_NO_MODULE_WITH_EVENT_AND_NAME =
        "No modules have key event(s) %s and contain %s in their module name!";
    public static final String MESSAGE_DISPLAYED_MODULES_WITH_NAME_AND_DATE =
        "Displayed modules with module name containing %s and key event(s) on %s!";
    public static final String MESSAGES_DISPLAYED_MODULES_WITH_EVENT_AND_NAME =
        "Displayed modules with key event(s) %s that contain %s in the module name!";
    public static final String MESSAGES_DISPLAYED_MODULES_WITH_EVENT_AND_NAME_AND_DATE =
        "Displayed modules with module names containing %s and with key event(s) of type %s "
            + "falling on %s!";
    public static final String MESSAGE_NO_MODULES_WITH_EVENT_AND_NAME_AND_DATE =
        "No modules have module names containing %s, with key event(s) of type %s falling on %s!";



}
