using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace WarsawCityGamesServer.MissionLoader.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            return View();
        }

        public ActionResult FileUpload(HttpPostedFileBase file, string name, string description, string )
        {
            if (file == null) return RedirectToAction("YourOffers", "Offers");
            try
            {
                using (MemoryStream ms = new MemoryStream())
                {
                    file.InputStream.CopyTo(ms);
                    byte[] array = ms.GetBuffer();
                    var firstOrDefault =
                        context.Customers.FirstOrDefault(x => x.User.UserName == User.Identity.Name);
                    if (firstOrDefault != null)
                        ViewBag.LoggedUserName = firstOrDefault.FirstName;
                    context.CouchsurfingOffers.First(x => x.Id == id).Image = array;
                    context.SaveChanges();
                }
            }
            catch (DbEntityValidationException e)
            {
                PrintDbEntryValidationExceptions(e);
            }
            return RedirectToAction("YourOffers", "Offers");
        }
    }
}