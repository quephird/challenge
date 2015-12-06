(ns main-test
  (:require [clojure.test :refer :all])
  (:import Main))

(deftest test-is-legible
  (testing "Legible accounts"
    (is (Main/isLegible "123456789"))
    (is (Main/isLegible "000000000")))
  (testing "Accounts with one illegible character"
    (is (not (Main/isLegible "12345678?")))
    (is (not (Main/isLegible "1234?6789")))
    (is (not (Main/isLegible "?23456789"))))
  (testing "Accounts with more than one illegible character"
    (is (not (Main/isLegible "1234?678?")))
    (is (not (Main/isLegible "?234?6789")))
    (is (not (Main/isLegible "?234567?9")))))

(deftest test-is-error
  (testing "Checksum errors"
    (testing "Invalid numbers"
      (is (Main/isError "000000011"))
      (is (Main/isError "012345678"))
      (is (Main/isError "987654321")))
    (testing "Valid numbers"
      (is (not (Main/isError "000000000")))
      (is (not (Main/isError "000000051")))
      (is (not (Main/isError "123456789")))
      (is (not (Main/isError "987654330"))))))

; (deftest test-add
;   (testing "foo"
;     (let [result (Main/myLength (Delayed/delayed [1 2 3 4]))]
;       (println (class result))
;       (is (= 0 (.eval result))))))

; (deftest test-parse-entries
;   (testing "Valid entries"
;     (is (= "123456789"
;           (Main/toAccount (list "    _  _     _  _  _  _  _ "
;                                 "  | _| _||_||_ |_   ||_||_|"
;                                 "  ||_  _|  | _||_|  ||_| _|"
;                                 ""))))))
  ;   (is (= "987654321"
  ;         (Main/toAccount [" _  _  _  _  _     _  _    "
  ;                          "|_||_|  ||_ |_ |_| _| _|  |"
  ;                          " _||_|  ||_| _|  | _||_   |"
  ;                          ""]))))
  ; (testing "Entry with missing trailing space in first line"
  ;   (is (= "12345678?"
  ;         (Main/toAccount ["    _  _     _  _  _  _  _"
  ;                          "  | _| _||_||_ |_   ||_||_|"
  ;                          "  ||_  _|  | _||_|  ||_| _|"
  ;                          ""]))))
  ; (testing "Entry with invalid 1"
  ;   (is (= "?23456789"
  ;         (Main/toAccount ["  | _  _     _  _  _  _  _ "
  ;                          "  | _| _||_||_ |_   ||_||_|"
  ;                          "  ||_  _|  | _||_|  ||_| _|"
  ;                          ""]))))
  ; (testing "Entry with missing line"
  ;   (is (= "?????????"
  ;         (Main/toAccount [" _  _  _  _  _     _  _    "
  ;                          "|_||_|  ||_ |_ |_| _| _|  |"])))))
