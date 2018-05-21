grammar InformalOpeningHours;

openings : LB* line (LB* line)* LB*;

line : day_part COLON time_part ;

day_part : day (day_span_separator day)? ;
day : DAY ;
day_span_separator : HYPHEN ;

time_part           : time time_span_separator time 'Uhr'? ;
time                : hour time_part_separator minute ;
hour                : TIME_DIGIT ;
minute              : TIME_DIGIT ;
time_part_separator : COLON | POINT ;
time_span_separator : HYPHEN ;

TIME_DIGIT : [0-9] [0-9] ;
COLON      : ':' ;
POINT      : '.' ;
HYPHEN     : '-' ;
LB         : '\n' | '\r\n' ;

DAY :
	'Mo'('ntag' | 'nday')? |
	'Di''enstag'? | 'Tu''esday'? |
	'Mi''ttwoch'? | 'We''dnesday'? |
	'Do''nnerstag'? | 'Th''ursday'? |
	'Fr'('eitag' | 'iday')? |
	'Sa'('mstag' | 'turday')? |
	'So''nntag'? | 'Su'('nday'?) ;

WS : ' ' -> skip ;
