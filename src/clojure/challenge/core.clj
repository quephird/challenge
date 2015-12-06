(ns challenge.core)

; Spec didn't actually include a "glyph" for zero but we'll assume
; this is correct. Also the topmost lines for the other glyphs
; in the original email were offset so we'll assume that was artifact.
(def zero (str " _ "
               "| |"
               "|_|"))

(def one (str "   "
              "  |"
              "  |"))

(def two (str " _ "
              " _|"
              "|_ "))

(def three (str " _ "
                " _|"
                " _|"))

(def four (str "   "
               "|_|"
               "  |"))

(def five (str " _ "
               "|_ "
               " _|"))

(def six (str " _ "
              "|_ "
              "|_|"))

(def seven (str " _ "
                "  |"
                "  |"))

(def eight (str " _ "
                "|_|"
                "|_|"))

(def nine (str " _ "
               "|_|"
               " _|"))

(def digit-lookups
  "Maps string representations of numbers to their respective char
   representations."
  (into {}
    (map vector
      [zero one two three four five six seven eight nine]
      [\0 \1 \2 \3 \4 \5 \6 \7 \8 \9])))

(defn entry->account [entry]
  "Takes a list of four lines representing an entry and converts it
   into an account number in string format. Unparseable numbers
   are returned as '?'s."
  (->> entry
    (take 3)                            ; Discard fourth line; it's blank
    (map #(partition-all 3 %))          ; Group characters into threes
    (apply map vector)                  ; Form tuples across three lines
    (map #(apply str (apply concat %))) ; Create strings from tuples
    (map #(get digit-lookups % "?"))    ; Lookup digits
    (apply str)))                       ; Put them all together

(defn parse-account-numbers [filename]
  "Takes a path to a file and returns the list of account numbers
   contained in it."
  (with-open [rdr (clojure.java.io/reader filename)]
    (let [accounts (->> rdr
                     line-seq             ; Read lines from the file
                     (partition-all 4)    ; Group them into entries
                     (map entry->account) ; Convert them to accounts
                     doall)]              ; Materialize the seq
      accounts)))

(defn illegible? [account]
  "Takes nine digit account number in string format and returns
   true if it has at least one '?' in it."
  (boolean (re-find #"\?" account)))

(defn error? [account]
  "Takes nine digit account number in string format and validates it."
  (as-> account $
    (map #(Integer/parseInt (str %)) $) ; Convert characters to integers
    (map * (range 9 0 -1) $)            ; Multiply each digit by its reverse position
    (reduce + 0 $)                      ; Sum all the products
    (rem $ 11)                          ; Take the modulus
    (not (zero? $))))                   ; Result should be zero

(defn print-report [accounts filename]
  "Takes a list of accounts and prints a report with
   them in the first column and their status in the second."
   (with-open [wtr (clojure.java.io/writer filename :append true)]
     (doseq [account accounts]
       (let [status (cond
                      (illegible? account) " ILLEGIBLE"
                      (error? account) " ERROR"
                      :else "")]
         (.write wtr (str account status "\n"))))))
