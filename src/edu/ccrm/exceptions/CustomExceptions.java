package edu.ccrm.exceptions;

public class DuplicateEnrollmentException extends Exception {
    public DuplicateEnrollmentException(String msg){ super(msg); }
}

public class MaxCreditLimitExceededException extends Exception {
    public MaxCreditLimitExceededException(String msg){ super(msg); }
}

public class NotFoundException extends Exception {
    public NotFoundException(String msg){ super(msg); }
}
