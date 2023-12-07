/*
  Warnings:

  - You are about to drop the column `filePath` on the `Data` table. All the data in the column will be lost.

*/
-- RedefineTables
PRAGMA foreign_keys=OFF;
CREATE TABLE "new_Data" (
    "id" TEXT NOT NULL PRIMARY KEY,
    "fileName" TEXT NOT NULL
);
INSERT INTO "new_Data" ("fileName", "id") SELECT "fileName", "id" FROM "Data";
DROP TABLE "Data";
ALTER TABLE "new_Data" RENAME TO "Data";
PRAGMA foreign_key_check;
PRAGMA foreign_keys=ON;
