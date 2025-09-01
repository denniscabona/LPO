//esercizio 1
let catAllAcc ls =
    let rec aux acc =
        function
        | hd::tl -> aux (hd + acc) tl
        | [] -> acc
    aux "" (List.rev ls)


//esercizio 2
let catAllFold ls = List.fold (fun acc elem -> acc + elem) "" ls