:- dynamic yes/1, no/1, display_language/1.
% default language
display_language(en).

% Start Point
start :-
    choose_language,
    write('-----------------------------------------------'), nl,
    write('      Integrated Medical Diagnosis System'), nl,
    write('-----------------------------------------------'), nl,
    show_disclaimer,
    diagnostic_loop.
    
choose_language :-
    write('Choose language (en/ar): '),
    read_line_to_string(user_input, LangString),
    string_lower(LangString, LangLower),
    atom_string(LangAtom, LangLower),
    retractall(display_language(_)),
    assertz(display_language(LangAtom)).

show_disclaimer :-
    display_language(ar) ->
        (
            write('تنبيه هام: هذا النظام لأغراض تعليمية فقط.'), nl,
            write('ولا يُعد بديلاً عن التشخيص الطبي المهني.'), nl,
            write('العلاجات المقترحة هي للإرشاد العام فقط. يُرجى استشارة طبيب قبل استخدامها.'), nl, nl
        )
    ;
        (
            write('Important Notice: This system is for educational purposes only.'), nl,
            write('It is not a substitute for professional medical diagnosis.'), nl,
            write('Suggested treatments are for general guidance. Please consult a doctor before use.'), nl, nl
        ).

% Disease knowledge base: disease(Name, SymptomsList, TotalSymptoms)
disease(flu, [fever, headache, body_ache, cough, sore_throat, fatigue], 6).
disease(common_cold, [runny_nose, sneezing, sore_throat, cough], 4).
disease(allergies, [sneezing, runny_nose, itchy_eyes], 3).
disease(strep_throat, [sore_throat, fever, swollen_glands, headache], 4).
disease(pneumonia, [fever, cough, chest_pain, shortness_of_breath, fatigue], 5).
disease(sinusitis, [headache, facial_pain, nasal_congestion, cough], 4).
disease(bronchitis, [cough, fatigue, shortness_of_breath, chest_discomfort], 4).

% Treatment knowledge base treatmeant(Name, TreatmentList)
treatment(flu, ['Rest at home', 'Warm fluids', 'Paracetamol (500–1000mg every 6 hours)', 'Oseltamivir for severe cases']).
treatment(common_cold, ['Rest', 'Honey with lemon', 'Saline nasal sprays', 'Acetaminophen for pain']).
treatment(allergies, ['Loratadine (10mg daily)', 'Nasal corticosteroid sprays', 'Avoid allergens']).
treatment(strep_throat, ['Penicillin V (500mg twice daily for 10 days)', 'Warm salt water gargle', 'Ibuprofen for pain']).
treatment(pneumonia, ['Amoxicillin-clavulanate (625mg every 8 hours)', 'Complete rest', 'Oxygen if saturation < 92%']).
treatment(sinusitis, ['Steroid nasal sprays', 'Oral decongestants', 'Saline nasal wash']).
treatment(bronchitis, ['Albuterol inhaler', 'Drink plenty of warm fluids', 'Rest']).

% Translations symptom_translation(Symptom, 'Symptom_Translation').
% disease_translation(Disease,Translation)
symptom_translation(fever, 'ارتفاع درجة الحرارة').
symptom_translation(headache, 'صداع').
symptom_translation(body_ache, 'آلام في الجسم').
symptom_translation(cough, 'سعال').
symptom_translation(sore_throat, 'التهاب الحلق').
symptom_translation(fatigue, 'إرهاق').
symptom_translation(runny_nose, 'سيلان الأنف').
symptom_translation(sneezing, 'عطس').
symptom_translation(itchy_eyes, 'حكة في العينين').
symptom_translation(swollen_glands, 'تورم الغدد').
symptom_translation(chest_pain, 'ألم في الصدر').
symptom_translation(shortness_of_breath, 'ضيق في التنفس').
symptom_translation(facial_pain, 'ألم في الوجه').
symptom_translation(nasal_congestion, 'احتقان الأنف').
symptom_translation(chest_discomfort, 'انزعاج في الصدر').

disease_translation(flu, 'الإنفلونزا').
disease_translation(common_cold, 'نزلة برد').
disease_translation(allergies, 'حساسية').
disease_translation(strep_throat, 'التهاب الحلق العقدي').
disease_translation(pneumonia, 'التهاب رئوي').
disease_translation(sinusitis, 'التهاب الجيوب الأنفية').
disease_translation(bronchitis, 'التهاب الشعب الهوائية').

% Label fetchers
get_symptom_label(Symptom, Label) :-
    display_language(ar),
    symptom_translation(Symptom, Label), !.
get_symptom_label(Symptom, Label) :-
    atom_string(Symptom, Label).

get_disease_label(Disease, Label) :-
    display_language(ar),
    disease_translation(Disease, Label), !.
get_disease_label(Disease, Label) :-
    atom_string(Disease, Label).

% Diagnostic Loop
diagnostic_loop :-
    nl,
    (display_language(ar) ->
        write('أدخل اسم المريض (أو "توقف" للخروج): ')
    ;
        write('Enter patient name (or type "stop" to exit): ')
    ),
    read_line_to_string(user_input, Patient),
    (Patient == "stop" ; Patient == "توقف" ->
        (display_language(ar) -> write('شكرًا لاستخدامك النظام. نتمنى لك الصحة!') ;
         write('Thank you for using the system. Stay healthy!')), nl
    ;
        nl,
        (display_language(ar) -> write('من فضلك أجب بـ  على كل عرض بنعم او لا.') ;
         write('Please answer with yes/no to each symptom.')), nl, nl,
        findall(disease(Disease, Percentage),
                (disease(Disease, Symptoms, TotalSymptoms),
                 count_matching_symptoms(Patient, Symptoms, Matched),
                 Percentage is (Matched / TotalSymptoms) * 100),
                AllDiseases),
        include([disease(_, P)] >> (P >= 25), AllDiseases, FilteredDiseases),
        present_results_with_treatments(Patient, FilteredDiseases),
        cleanup_session,
        diagnostic_loop
    ).

% Ask about a symptom
ask(Patient, Symptom) :-
    get_symptom_label(Symptom, Label),
    (display_language(ar) ->
        format('~w، هل تعاني من ~w؟ (نعم/لا): ', [Patient, Label])
    ;
        format('~w, do you have ~w? (yes/no): ', [Patient, Label])),
    read_line_to_string(user_input, RawResponse),
    downcase_atom(RawResponse, LowerResponse),
    process_response(LowerResponse, Symptom, Patient).

% Process response
process_response(Response, Symptom,Patient) :-
    (memberchk(Response, ['yes', 'y','نعم']) ->
        assertz(yes(Symptom))
    ; memberchk(Response, ['no', 'n','لا']) ->
        assertz(no(Symptom)), fail
    ; (display_language(ar) -> write('إجابة غير صحيحة. من فضلك أجب بـ بنعم او لا أو .') ;
     write('Invalid answer. Please type yes or no.')), nl,
      ask(Patient, Symptom)
    ).
% Symptom checking
verify_symptom(Patient, Symptom) :-
    (yes(Symptom) -> true ;
     no(Symptom) -> fail ;
     ask(Patient, Symptom)).

% counting_matching_symptoms(Patient, Symptoms,Count) // count is returned as number of matched symptoms
count_matching_symptoms(Patient, Symptoms, Count) :-
    findall(S, (member(S, Symptoms), verify_symptom(Patient, S)), MatchedSymptoms),
    length(MatchedSymptoms, Count).

% Diagnosis display
present_results_with_treatments(_, []) :-
    nl,
    (display_language(ar) ->
        (write('لا يوجد تشخيص واضح بناءً على الأعراض المدخلة.'), nl,
         write('ننصح بمراجعة طبيب مختص.'), nl)
    ;
        (write('No clear diagnoses based on provided symptoms.'), nl,
         write('We recommend consulting a medical professional.'), nl)).
         
present_results_with_treatments(Patient, Diseases) :-
    nl,
    (display_language(ar) ->
        format('~w، بناءً على الأعراض التي أدخلتها، قد تكون الحالات التالية:~n~n', [Patient])
    ;
        format('~w, based on the symptoms you entered, possible conditions are:~n~n', [Patient])),
    sort_diseases(Diseases, Sorted),
    print_diagnoses_with_treatments(Sorted),
    nl,
    (display_language(ar) ->
        write('تنبيه: هذه النتائج إرشادية فقط. يُرجى استشارة الطبيب قبل تناول أي علاج.')
    ;
        write('Note: These results are for guidance only. Please consult a doctor before starting any treatment.')), nl.
        
print_diagnoses_with_treatments([]).
print_diagnoses_with_treatments([disease(Disease, Percentage) | Rest]) :-
    get_disease_label(Disease, Label),
    format('- ~w: ~1f%~n', [Label, Percentage]),
    (Percentage >= 50 ->
        (display_language(ar) -> write('  العلاج المقترح:~n') ; write('  Suggested treatment:~n')),
        print_treatments(Disease)
    ;
        (display_language(ar) -> write('  (نسبة الثقة منخفضة. لا يُنصح بالعلاج دون استشارة طبية)') ;
         write('  (Low confidence. Do not treat without professional advice)')), nl
    ),
    print_diagnoses_with_treatments(Rest).

print_treatments(Disease) :-
    treatment(Disease, Treatments),
    print_treatment_list(Treatments).

print_treatment_list([]).
print_treatment_list([Treatment | Rest]) :-
    format('  * ~w~n', [Treatment]),
    print_treatment_list(Rest).

% Sorting and cleanup
% pred sort is built in function  predsort(compare_disease,FilteredDiseases,Sorted).
% compare_disease(<,disease(_,P1), disease(_, P2):- P1<P2
% return a list of sorted diseases
sort_diseases(Diseases, Sorted) :-
    predsort(compare_diseases, Diseases, Sorted).
compare_diseases(<, disease(_, P1), disease(_, P2)) :- P1 < P2.
compare_diseases(>, disease(_, P1), disease(_, P2)) :- P1 > P2.
compare_diseases(=, _, _).
cleanup_session :-
    retractall(yes(_)),
    retractall(no(_)).


% converting stream of input into string
% first convert stream of input into codes of ascii
% second convert stream of codes into string.
% two functions are built in
read_line_to_string(Stream, String) :-
    read_line_to_codes(Stream, Codes),
    string_codes(String, Codes).
