(ns challenge.core-test
  (:require [clojure.test :refer :all]
            [challenge.core :refer :all]))

(deftest test-illegibility
  (testing "Illegibility"
    (testing "Legible numbers"
      (is (not (illegible? "123456789")))
      (is (not (illegible? "000000000"))))
    (testing "Numbers with one illegible character"
      (is (illegible? "?23456789"))
      (is (illegible? "12345678?")))
    (testing "Numbers with more than on illegible character"
      (is (illegible? "1234??789"))
      (is (illegible? "?234?67?9"))
      (is (illegible? "?????????")))))

(deftest test-checksum-errors
  (testing "Checksum errors"
    (testing "Invalid numbers"
      (is (error? "000000011"))
      (is (error? "012345678"))
      (is (error? "987654321")))
    (testing "Valid numbers"
      (is (not (error? "000000000")))
      (is (not (error? "000000051")))
      (is (not (error? "123456789")))
      (is (not (error? "987654330"))))))

(deftest test-parse-entries
  (testing "Valid entries"
    (is (= "123456789"
          (entry->account ["    _  _     _  _  _  _  _ "
                           "  | _| _||_||_ |_   ||_||_|"
                           "  ||_  _|  | _||_|  ||_| _|"
                           ""])))
    (is (= "987654321"
          (entry->account [" _  _  _  _  _     _  _    "
                           "|_||_|  ||_ |_ |_| _| _|  |"
                           " _||_|  ||_| _|  | _||_   |"
                           ""]))))
  (testing "Entry with missing trailing space in first line"
    (is (= "12345678?"
          (entry->account ["    _  _     _  _  _  _  _"
                           "  | _| _||_||_ |_   ||_||_|"
                           "  ||_  _|  | _||_|  ||_| _|"
                           ""]))))
  (testing "Entry with invalid 1"
    (is (= "?23456789"
          (entry->account ["  | _  _     _  _  _  _  _ "
                           "  | _| _||_||_ |_   ||_||_|"
                           "  ||_  _|  | _||_|  ||_| _|"
                           ""]))))
  (testing "Entry with missing line"
    (is (= "?????????"
          (entry->account [" _  _  _  _  _     _  _    "
                           "|_||_|  ||_ |_ |_| _| _|  |"])))))

(deftest test-print-report
  (testing "Output of test data"
    (let [test-accounts        ["457508000" "664371495" "86110??36"]
          test-report-filename "test_report.txt"
          test-report          (print-report test-accounts test-report-filename)]
      (is (= "457508000\n664371495 ERROR\n86110??36 ILLEGIBLE\n"
             (slurp test-report-filename)))
      (clojure.java.io/delete-file test-report-filename))))

(deftest test-front-to-back
  (testing "Reading of file and printing report"
    (let [test-data-filename   "test/clojure/challenge/test_data.txt"
          test-accounts        (parse-account-numbers test-data-filename)
          test-report-filename "test_report.txt"
          test-report          (print-report test-accounts test-report-filename)]
      (is (= "457508000\n664371495 ERROR\n86110??36 ILLEGIBLE\n"
             (slurp test-report-filename)))
      (clojure.java.io/delete-file test-report-filename))))

