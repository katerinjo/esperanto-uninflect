(ns espo-uninflect.core)

(def irregulars
  #{"la" "l'" "ne" "kaj" "aŭ" "kaŭ" "sed" "jes" "ĉu" "pri" "super" "trans" "post" "kontraŭ" "inter"
    "ĉirkaŭ" "ĉe" "antaŭ" "post" "sub" "apud" "inter" "preter" "de" "per" "malgraŭ" "dum" "krom" "pro"
    "por" "el" "en" "sur" "ekster" "super" "po" "plus" "ekde" "ol" "tra" "ĝis" "al" "malkiel" "kun"
    "sen" "laŭ" "anstataŭ" "kia" "kial" "kiam" "kie" "kien" "kiel" "kies" "kio" "kiom" "kiu" "tia"
    "tial" "tiam" "tie" "tien" "tiel" "ties" "tio" "tiom" "tiu" "ia" "ial" "iam" "ie" "ien" "iel"
    "ies" "io" "iom" "iu" "ĉia" "ĉial" "ĉiam" "ĉie" "ĉien" "ĉiel" "ĉies" "ĉio" "ĉiom" "ĉiu" "nenia"
    "nenial" "neniam" "nenie" "nenien" "neniel" "nenies" "nenio" "neniom" "neniu" "ajn" "ĉi" "pli"
    "plej" "ja" "kaj/aŭ" "bis" "ju" "des" "kion" "tion" "ĉion" "nenion" "kiun" "tiun" "ĉiun"
    "neniun" "alio" "aliu" "alies" "aliam" "alial" "alie" "alien"})

(def pronouns
  {"mi" "mi"
   "min" "mi"
   "vi" "vi"
   "vin" "vi"
   "li" "li"
   "lin" "li"
   "ŝi" "ŝi"
   "ŝin" "ŝi"
   "ĝi" "ĝi"
   "ĝin" "ĝi"
   "ili" "ili"
   "ilin" "ili"
   "ni" "ni"
   "nin" "ni"
   "si" "si"
   "sin" "si"
   "oni" "oni"
   "onin" "oni"
   "ri" "ri"
   "rin" "ri"
   "ŝli" "ŝli"
   "ŝlin" "ŝli"
   "ŝ/li" "ŝ/li"
   "ŝ/lin" "ŝ/li"
   "mia" "mia"
   "mian" "mia"
   "via" "via"
   "vian" "via"
   "lia" "lia"
   "lian" "lia"
   "ŝia" "ŝia"
   "ŝian" "ŝia"
   "ĝia" "ĝia"
   "ĝian" "ĝia"
   "ilia" "ilia"
   "ilian" "ilia"
   "nia" "nia"
   "nian" "nia"
   "sia" "sia"
   "sian" "sia"
   "onia" "onia"
   "onian" "onia"
   "ria" "ria"
   "rian" "ria"
   "ŝlia" "ŝlia"
   "ŝlian" "ŝlia"
   "ŝ/lia" "ŝ/lia"
   "ŝ/lian" "ŝ/lia"
   "miaj" "mia"
   "miajn" "mia"
   "viaj" "via"
   "viajn" "via"
   "liaj" "lia"
   "liajn" "lia"
   "ŝiaj" "ŝia"
   "ŝiajn" "ŝia"
   "ĝiaj" "ĝia"
   "ĝiajn" "ĝia"
   "iliaj" "ilia"
   "iliajn" "ilia"
   "niaj" "nia"
   "niajn" "nia"
   "siaj" "sia"
   "siajn" "sia"
   "oniaj" "onia"
   "oniajn" "onia"
   "riaj" "ria"
   "riajn" "ria"
   "ŝliaj" "ŝlia"
   "ŝliajn" "ŝlia"
   "ŝ/liaj" "ŝ/lia"
   "ŝ/liajn" "ŝ/lia"})

(defn lower-x-system-convert
  "use lowercase Esperanto text using the x-system and produce the equivalent in standard orthography"
  [x]
  (-> x
    (clojure.string/replace #"cx" "ĉ")
    (clojure.string/replace #"gx" "ĝ")
    (clojure.string/replace #"hx" "ĥ")
    (clojure.string/replace #"jx" "ĵ")
    (clojure.string/replace #"sx" "ŝ")
    (clojure.string/replace #"ux" "ŭ")))

(defn format
  "make a word lowercase and convert Esperanto x digraphs to unicode single letters"
  [raw]
  (-> raw lower-case lower-x-system-convert))

(defn to-base
  "reduce word to base form by using regex to find the word's root (the first group)"
  [word regex ending]
  (let [base (get (re-matches regex word) 1)]
    (when base
      (str base ending))))

(defn reduce-verb
  "if the given lowercase word is a verb, return dictionary form, else nil"
  [word]
  (to-base word #"(\w+)([iaou]s|u)" "i"))

(defn reduce-noun
  "if the given lowercase word is a noun, return the dictionary form, else return nil"
  [word]
  (to-base word #"(\w+)(o|oj|on|ojn)" "o"))

(defn reduce-adverb
  "if the given lowercase word is an adverb, return dictionary form, else nil"
  [word]
  (to-base word #"(\w+)en?" "e"))

(defn uninflect
  "reduce an Esperanto word to its dictionary form"
  [raw]
  (let [word (format raw)]
    (or
      (when (contains? irregulars word) word)
      (get pronouns word)
      (reduce-verb word)
      (reduce-noun word)
      (reduce-adverb word)
      word)))
