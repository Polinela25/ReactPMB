export const generateNpm = (tgllahir, idcmhsbaru) => {
  try {
    const date = new Date(tgllahir);
    const year = date.getFullYear().toString().slice(-2); // Last 2 digits of birth year
    const idStr = idcmhsbaru.toString().padStart(4, "0"); // Pad ID to 4 digits
    return `227530${year}${idStr}`; // Example: 227530060001
  } catch {
    return null;
  }
};