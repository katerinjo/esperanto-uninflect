(ns espo-uninflect.core-test
  (:require [clojure.test :refer :all]
            [espo-uninflect.core :refer :all]))

(deftest test-nouns
  (testing "nouns: singular, plural, accusative, both, and the unofficial genitive"
    (is
      (and
        (= "ĥoro " (uninflect "ĥoro"))
        (= "no" (uninflect "noj"))
        (= "aero" (uninflect "aeron"))
        (= "bordo" (uninflect "bordojn"))
        (= "cigaredo" (uninflect "cigaredes"))))))

(deftest test-adjectives
  (testing "adjectives: singular, plural, accusative, and both"
    (is
      (and
        (= "densa" (uninflect "densa"))
        (= "plura" (uninflect "pluraj"))
        (= "flava" (uninflect "flavan"))
        (= "gaja" (uninflect "gajajn"))
        (= "ĝusta" (uninflect "gxusta"))))))

(deftest test-adverbs
  (testing "adverbs: normal, directional"
    (is
      (and
        (= "hejme" (uninflect "hejmen"))
        (= "denove" (uninflect "denove"))))))

(deftest test-verbs
  (testing "verbs: infinitive, past, present, future, subjunctive, imperative"
    (is
      (and
        (= "erari" (uninflect "eraras"))
        (= "ĵeti" (uninflect "ĵeti"))
        (= "kanti" (uninflect "kantos"))
        (= "varbi" (uninflect "varbis"))
        (= "zorgi" (uninflect "zorgu"))
        (= "helpi" (uninflect "helpus"))))))

(deftest test-correlatives
  (testing "correlatives: standard, but also the ali- series"
    (is
      (and
        (= "kiu" (uninflect "kiuj"))
        (= "tio" (uninflect "tion"))
        (= "ia" (uninflect "ia"))
        (= "nenie" (uninflect "nenien"))
        (= "aliel" (uninflect "aliel"))))))

(deftest test-pronouns
  (testing "pronouns: plain, possessive, possessive plural, possessive acc, and possessive both"
    (is
      (and
        (= "ilia" (uninflect "ilia"))
        (= "ŝ/lia" (uninflect "sx/liaj"))
        (= "mi" (uninflect "mi"))
        (= "ri" (uninflect "ri"))
        (= "onia" (uninflect "onian"))))))

(deftest test-particles
  (testing "particles: prepositions et cetera"
    (is
      (and
        (= "cent" (uninflect "cent"))
        (= "ĉirkaŭ" (uninflect "cxirkaux"))
        (= "je" (uninflect "je"))
        (= "pri" (uninflect "pri"))
        (= "kvazaŭ" (uninflect "kvazaux"))))))
