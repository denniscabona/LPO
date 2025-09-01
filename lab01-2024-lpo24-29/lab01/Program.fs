//esercizio 1
let rec mulAllAux ls =
    match ls with
        | hd::tl -> hd * mulAllAux tl
        | [] -> 1
        
let rec mulAll ls =
    match ls with
        | hd::_ -> mulAllAux ls
        | [] -> 0


//esercizio 2
let rec isIn el ls =
  match ls with
    | hd::tl -> hd = el || isIn el tl
    | [] -> false


//esercizio 3
let rec insert el ls =
    match ls with
      | hd::tl -> if hd = el then ls else hd::insert el tl
      | [] -> [el]



//esercizio 4
let rec insert el ls =
    if (isIn el ls) then ls else ls @ el::[] 


// esercizio 5
let rec odd ls =
  match ls with
    | _::hd::tl -> hd::odd tl
    | [] -> []


//esercizio 6
let rec ordInsert el ls =
  match ls with
    | hd::tl when el = hd -> hd::tl
    | hd::tl -> if (el < hd) then el::hd::tl
                else hd::ordInsert el tl
    | []->[el]


//esercizio 7  
let ordInsert el ls =
  let rec aux acc rest =
    match rest with
    | hd::tl when el = hd -> List.rev (hd :: acc) @ tl
    | hd::tl ->
        if el < hd then List.rev (el :: acc) @ rest
        else aux (hd :: acc) tl
    | [] -> List.rev (el :: acc)
  in
  aux [] ls


//esercizio 8
let rec merge ls1 ls2 =
  match ls1,ls2 with
    | h1::t1, h2::t2 -> if(h1 = h2) then h1::merge t1 t2
                        elif(h1 < h2) then h1::merge t1 ls2
                        else h2::merge ls1 t2
    | [], _ -> ls2  //Se la prima lista è vuota (finisce) allora concateno la seconda       ls2 è solo un nome. di fatto utilizza tl
    | _ -> ls1      // _, [] -> ls1 Se la seconda lista è vuota (finisce) allora concateno la prima